package com.juliadanylyk.shift.network

@Suppress("unused")
sealed class RequestResult<out T : Any> {

    class Success<out T : Any>(val data: T) : RequestResult<T>()

    class Failure : RequestResult<Nothing>()
}