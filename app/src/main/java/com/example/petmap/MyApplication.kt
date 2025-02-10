package com.example.petmap

import android.app.Application
import com.example.petmap.data.repository.AppDataRepo

class MyApplication: Application() {
    val appDataRepo by lazy { AppDataRepo(this) }
}