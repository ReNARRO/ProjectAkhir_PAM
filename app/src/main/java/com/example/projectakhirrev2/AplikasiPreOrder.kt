package com.example.projectakhirrev2

import android.app.Application
import com.example.projectakhirrev2.repositori.ContainerDataApp

class AplikasiPreOrder: Application() {
    lateinit var container: ContainerDataApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}