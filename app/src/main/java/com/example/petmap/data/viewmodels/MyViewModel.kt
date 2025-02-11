package com.example.petmap.data.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import com.example.petmap.MyApplication
import com.example.petmap.data.repository.AppDataRepo
import com.example.petmap.data.repository.BroadcastPetLostMessage
import com.example.petmap.data.repository.Home
import com.example.petmap.data.repository.Message
import com.example.petmap.data.repository.Pet
import com.example.petmap.data.repository.UpdatePetLocation
import com.example.petmap.data.repository.UserFullInfo
import com.example.petmap.data.repository.petMapApi

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

    /** 更新用户信息 */
    suspend fun updateFullInfo() {
        val fullInfo = userFullInfo()
        // 随机更新宠物的位置
        // 若宠物离家太远就广播宠物走失消息
        val pets = fullInfo.pets.map { randomLocation(it) }
        fullInfo.home?.let {
            for (pet in pets) {
                updatePetLocation(pet)
                val m = calculateDistance(pet, it)
                if (m >= 600) {
                    Log.i("MyViewModel", "${pet.petName} 触发走丢广播")
                    broadcastPetLostMessage(pet)
                }
            }
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
                pet.petName,
                pet.owner,
                pet.longitude,
                pet.latitude
            )
        )
    }

    /** 随机化宠物的位置 */
    private fun randomLocation(pet: Pet) = Pet(
        petName = pet.petName,
        owner = pet.owner,
        longitude = pet.longitude + (-8..8).random() / 1000, // 0.004 约为 560m
        latitude = pet.longitude + (-8..8).random() / 1000,
    )

    /** 计算宠物和家的距离 */
    private fun calculateDistance(pet: Pet, home: Home): Float {
        val latLng1 = LatLng(pet.latitude, pet.longitude)
        val latLng2 = LatLng(home.latitude, home.longitude)
        return AMapUtils.calculateLineDistance(latLng1, latLng2)
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