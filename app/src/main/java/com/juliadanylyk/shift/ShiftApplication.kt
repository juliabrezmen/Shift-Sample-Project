package com.juliadanylyk.shift

import android.app.Application

class ShiftApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Dependencies.DEPENDENCIES.init(this)
    }
}