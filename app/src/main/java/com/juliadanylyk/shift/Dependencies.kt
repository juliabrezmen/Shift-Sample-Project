package com.juliadanylyk.shift

import com.juliadanylyk.shift.db.ShiftDao
import com.juliadanylyk.shift.imageloader.ImageLoader
import com.juliadanylyk.shift.logger.Logger
import com.juliadanylyk.shift.repository.ShiftRepository

object Dependencies {

    lateinit var shiftDao: ShiftDao
    lateinit var logger: Logger
    lateinit var imageLoader: ImageLoader
    lateinit var shiftRepository: ShiftRepository

    fun init(factory: DependenciesFactory) {
        shiftDao = factory.shiftDao()
        logger = factory.logger()
        imageLoader = factory.imageLoader()
        shiftRepository = factory.shiftRepository()
    }
}

interface DependenciesFactory {

    fun shiftDao():ShiftDao

    fun logger(): Logger

    fun imageLoader(): ImageLoader

    fun shiftRepository(): ShiftRepository
}