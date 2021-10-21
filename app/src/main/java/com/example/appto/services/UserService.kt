package com.example.appto.services

import com.example.appto.clients.Retrofit
import com.example.appto.models.User
import retrofit2.http.*

interface UserService {
    @POST("users")
    suspend fun register(@Body user: User): User

    @POST("users/login")
    fun login(@Body user: User): User

    @POST("users/logout")
    fun logout(@Header("Authorization") token: String): Void

    @GET("users/me")
    fun getUser(@Header("Authorization") token: String): User

    @DELETE("users/me")
    fun delete(@Header("Authorization") token: String): Void
}

val userService: UserService = Retrofit.restClient.create(UserService::class.java)