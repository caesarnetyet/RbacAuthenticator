package com.rbac.data.repositories

import com.rbac.data.models.User

interface IUserRepository {
    suspend fun login(email: String, password: String): Result<String>
    suspend fun logout(): Result<Unit>
    suspend fun getAuthToken(code: String): Result<String>
    suspend fun getUser(): Result<User>

}