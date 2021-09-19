package com.example.bigoweb.resources

import android.app.Application

class InitSP : Application() {

    companion object{
        lateinit var spITEM : SPManager

    }
    override fun onCreate() {
        super.onCreate()
        spITEM = SPManager(applicationContext)
    }
}