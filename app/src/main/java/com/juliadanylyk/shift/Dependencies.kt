package com.juliadanylyk.shift

import android.content.Context
import com.juliadanylyk.shift.data.repository.ShiftRepository
import com.juliadanylyk.shift.data.repository.ShiftRepositoryImpl
import com.juliadanylyk.shift.imageloader.ImageLoader
import com.juliadanylyk.shift.logger.Logger
import com.juliadanylyk.shift.logger.LoggerImpl
import com.juliadanylyk.shift.network.ApiClient
import com.juliadanylyk.shift.network.ShiftService

enum class Dependencies {
    DEPENDENCIES;

    lateinit var logger: Logger
    lateinit var imageLoader: ImageLoader
    lateinit var shiftRepository: ShiftRepository

    fun init(context: Context) {
        val shiftService: ShiftService = ApiClient().getShiftService()
        logger = LoggerImpl()
        imageLoader = ImageLoader(context)
        shiftRepository = ShiftRepositoryImpl(shiftService)
    }

}