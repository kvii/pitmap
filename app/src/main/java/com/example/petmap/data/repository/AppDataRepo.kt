package com.example.petmap.data.repository

import android.content.Context

class AppDataRepo(context: Context) {
    companion object {
        private const val KEY_USER_NAME = "user_name"
    }

    private val sp = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)

    var userName: String?
        get() = sp.getString(KEY_USER_NAME, null)
        set(value) {
            sp.edit().putString(KEY_USER_NAME, value).apply()
        }
}
