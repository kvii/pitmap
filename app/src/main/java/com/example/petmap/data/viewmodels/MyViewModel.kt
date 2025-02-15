package com.example.petmap.data.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.amap.api.maps.AMapUtils
import com.example.petmap.MyApplication
import com.example.petmap.data.repository.AppDataRepo
import com.example.petmap.data.repository.BroadcastPetLostMessage
import com.example.petmap.data.repository.Home
import com.example.petmap.data.repository.Message
import com.example.petmap.data.repository.Pet
import com.example.petmap.data.repository.UpdatePetLocation
import com.example.petmap.data.repository.UserFullInfo
import com.example.petmap.data.repository.petMapApi
import kotlinx.coroutines.delay

/** “我的”数据 */
class MyViewModel(private val appData: AppDataRepo) : ViewModel() {
    suspend fun userFullInfo(): UserFullInfo {
        val userName = appData.userName
        if (userName.isNullOrEmpty()) {
            throw IllegalArgumentException("用户名不能为空")
        }
        return petMapApi.getUserFullInfo(userName)
    }

    suspend fun userMessages(): List<Message> {
        val userName = appData.userName
        if (userName.isNullOrEmpty()) {
            throw IllegalArgumentException("用户名不能为空")
        }
        return petMapApi.getMessages(userName)
    }

    private val _fullInfo = MutableLiveData<UserFullInfo?>(null)

    /** 用户信息 */
    val fullInfo: LiveData<UserFullInfo?> get() = _fullInfo

    /** 定期更新用户信息 */
    suspend fun loop() {
        while (true) {
            try {
                updateFullInfo()
                Log.d("MyViewModel", "用户信息更新成功")
            } catch (e: Exception) {
                Log.e("MyViewModel", "用户信息更新失败", e)
            }
            delay(10 * 1000)
        }
    }

    /** 更新用户信息 */
    private suspend fun updateFullInfo() {
        val fullInfo = userFullInfo()
        if (fullInfo.home == null) {
            _fullInfo.value = fullInfo
            return
        }

        // 随机更新宠物的位置
        // 若宠物离家太远就广播宠物走失消息
        val pets = fullInfo.pets.map {
            val pet = randomLocation(it, fullInfo.home)
            updatePetLocation(pet)
            val m = calculateDistance(pet, fullInfo.home)
            Log.d("MyViewModel", "${pet.petName} 距离家 $m 米")
            if (m >= 500) {
                Log.i("MyViewModel", "${pet.petName} 触发走丢广播")
                broadcastPetLostMessage(pet)
            }
            pet
        }

        _fullInfo.value = UserFullInfo(
            user = fullInfo.user,
            home = fullInfo.home,
            pets = pets,
        )
    }

    /** 广播宠物丢失信息 */
    private suspend fun broadcastPetLostMessage(pet: Pet) {
        petMapApi.broadcastPetLostMessage(BroadcastPetLostMessage(pet.petName, pet.owner))
    }

    /** 更新宠物位置 */
    private suspend fun updatePetLocation(pet: Pet) {
        petMapApi.updatePetLocation(
            UpdatePetLocation(
                petName = pet.petName,
                owner = pet.owner,
                latitude = pet.latitude,
                longitude = pet.longitude,
            )
        )
    }

    /** 随机化宠物的位置 */
    private fun randomLocation(pet: Pet, home: Home) = Pet(
        petName = pet.petName,
        owner = pet.owner,
        latitude = home.latitude + randD(0.0055), // lat ±0.0055: 611.57214
        longitude = home.longitude + randD(0.0065), // lng ±0.0065: 585.0874
    )

    private fun randD(d: Double) = Math.random() * d * 2 - d

    /** 计算宠物和家的距离 */
    private fun calculateDistance(pet: Pet, home: Home): Float {
        return AMapUtils.calculateLineDistance(pet.latLang, home.latLang)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as MyApplication)
                MyViewModel(app.appDataRepo)
            }
        }
    }
}
