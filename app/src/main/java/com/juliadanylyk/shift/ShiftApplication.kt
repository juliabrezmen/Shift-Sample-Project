package com.juliadanylyk.shift

import android.app.Application
import com.juliadanylyk.shift.data.repository.ShiftRepositoryImpl
import com.juliadanylyk.shift.imageloader.ImageLoader
import com.juliadanylyk.shift.logger.LoggerImpl
import com.juliadanylyk.shift.network.ApiClient

class ShiftApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Dependencies.init(object : DependenciesFactory {
            override fun logger() = LoggerImpl()

            override fun imageLoader() = ImageLoader(this@ShiftApplication)

            override fun shiftRepository() = ShiftRepositoryImpl(ApiClient().getShiftService())
        })
    }
}