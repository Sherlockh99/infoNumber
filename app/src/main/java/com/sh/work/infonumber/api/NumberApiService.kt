package com.sh.work.infonumber.api

import retrofit2.http.GET
import retrofit2.http.Path

interface NumberApiService {
    @GET("{number}")
    suspend fun getInfoNumber(@Path("number") number: Int): String

    @GET("random/math")
    suspend fun getRandomInfo(): String
}