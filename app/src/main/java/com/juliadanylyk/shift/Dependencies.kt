package com.juliadanylyk.shift

import com.juliadanylyk.shift.data.repository.ShiftRepository
import com.juliadanylyk.shift.imageloader.ImageLoader
import com.juliadanylyk.shift.logger.Logger

object Dependencies {

    lateinit var logger: Logger
    lateinit var imageLoader: ImageLoader
    lateinit var shiftRepository: ShiftRepository

    fun init(factory: DependenciesFactory) {
        logger = factory.logger()
        imageLoader = factory.imageLoader()
        shiftRepository = factory.shiftRepository()
    }
}

interface DependenciesFactory {

    fun logger(): Logger

    fun imageLoader(): ImageLoader

    fun shiftRepository(): ShiftRepository
}