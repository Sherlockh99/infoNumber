package com.sh.work.infonumber.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitSingleton {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://numbersapi.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val api: NumberApiService = retrofit.create(NumberApiService::class.java)
}