package com.example.petmap.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.petmap.MyApplication
import com.example.petmap.data.repository.AppDataRepo
import com.example.petmap.data.repository.GetUser
import com.example.petmap.data.repository.petMapApi

class UserViewModel(private val appData: AppDataRepo) : ViewModel() {
    // demo 工程 没做自动登录逻辑

    fun loadUserName() = appData.userName

    /** 可能会抛异常 */
    suspend fun login(userName: String, password: String) {
        if (userName.isEmpty()) {
            throw IllegalArgumentException("用户名不能为空")
        }
        if (password.isEmpty()) {
            throw IllegalArgumentException("密码不能为空")
        }
        petMapApi.login(GetUser(userName, password))
        appData.userName = userName
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as MyApplication)
                UserViewModel(app.appDataRepo)
            }
        }
    }
}