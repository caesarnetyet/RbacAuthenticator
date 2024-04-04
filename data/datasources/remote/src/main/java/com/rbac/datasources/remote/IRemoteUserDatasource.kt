package com.rbac.datasources.remote

import com.rbac.core.networking.response.ErrorResponse
import com.rbac.core.networking.response.TokenResponse
import com.rbac.core.networking.response.UserResponse

interface IRemoteUserDatasource {
    suspend fun login(email: String, password: String): ApiResult<TokenResponse>
    suspend fun logout(): ApiResult<Unit>
    suspend fun fetchAuthToken(code: String): ApiResult<TokenResponse>
    suspend fun fetchUser(): ApiResult<UserResponse>
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val errorResponse: ErrorResponse) : ApiResult<Nothing>()
}
