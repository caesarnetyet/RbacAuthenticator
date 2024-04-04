package com.rbac.core.networking

import com.rbac.core.networking.request.LoginRequest
import com.rbac.core.networking.request.TokenRequest
import com.rbac.core.networking.response.TokenResponse
import com.rbac.core.networking.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IRbacApiService {
    @GET("user")
    suspend fun getUser(): UserResponse

    @POST("mobile/login")
    suspend fun login(@Body login: LoginRequest): TokenResponse

    @GET("logout")
    suspend fun logout()

    @POST("mobile/verify-token")
    suspend fun fetchAuthToken(@Body tokenRequest: TokenRequest): TokenResponse

}