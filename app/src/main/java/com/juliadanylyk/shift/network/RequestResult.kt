package com.juliadanylyk.shift.network

sealed class RequestResult {
    class Success : RequestResult()
    class Failure : RequestResult()
}