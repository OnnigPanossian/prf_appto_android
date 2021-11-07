package com.example.appto.clients

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Retrofit {

    companion object {
        val restClient: Retrofit = Retrofit.Builder()
            .baseUrl("https://prf-appto.herokuapp.com/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

}