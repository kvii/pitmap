package com.example.petmap.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.petmap.MyApplication
import com.example.petmap.data.repository.AppDataRepo
import com.example.petmap.data.repository.BroadcastPetLostMessage
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

    suspend fun broadcastPetLostMessage(pet: Pet) {
        petMapApi.broadcastPetLostMessage(BroadcastPetLostMessage(pet.petName, pet.owner))
    }

    suspend fun updatePetLocation(pet: Pet) {
        petMapApi.updatePetLocation(
            UpdatePetLocation(
                pet.petName,
                pet.owner,
                pet.longitude,
                pet.latitude
            )
        )
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