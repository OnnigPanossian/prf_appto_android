package com.example.appto.services

import com.example.appto.clients.Retrofit
import com.example.appto.models.AuthRequest
import com.example.appto.models.UpdateUserRequest
import com.example.appto.models.User
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("users")
    suspend fun register(@Body user: AuthRequest): Response<User>

    @POST("users/login")
    suspend fun login(@Body user: AuthRequest): Response<User>

    @POST("users/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Void>

    @GET("users/me")
    suspend fun getUser(@Header("Authorization") token: String?): Response<User>

    @PATCH("users")
    suspend fun updateUser(@Header("Authorization") token: String?, @Body user: UpdateUserRequest): Response<User>

    @DELETE("users/me")
    suspend fun delete(@Header("Authorization") token: String): Response<Void>
}

val userService: UserService = Retrofit.restClient.create(UserService::class.java)