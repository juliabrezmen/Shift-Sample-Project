package com.juliadanylyk.shift.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        requestBuilder.addHeader(AUTH_KEY, AUTH_TOKEN)

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTH_KEY = "Authorization"
        private const val AUTH_TOKEN = "Deputy b43b0ad1e8108e7ab870d7a54feac93ae8b8600e"
    }
}