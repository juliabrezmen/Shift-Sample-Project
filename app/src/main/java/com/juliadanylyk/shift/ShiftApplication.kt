package com.juliadanylyk.shift

import android.app.Application
import com.juliadanylyk.shift.db.ShiftDatabase
import com.juliadanylyk.shift.imageloader.ImageLoader
import com.juliadanylyk.shift.logger.LoggerImpl
import com.juliadanylyk.shift.network.ApiClient
import com.juliadanylyk.shift.repository.ShiftRepositoryImpl

class ShiftApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Dependencies.init(object : DependenciesFactory {
            private val shiftDatabase = ShiftDatabase.create(applicationContext)

            override fun shiftDao()  = shiftDatabase.shiftDao()

            override fun logger() = LoggerImpl()

            override fun imageLoader() = ImageLoader(this@ShiftApplication)

            override fun shiftRepository() = ShiftRepositoryImpl(ApiClient().getShiftService(), shiftDatabase.shiftDao())
        })
    }
}