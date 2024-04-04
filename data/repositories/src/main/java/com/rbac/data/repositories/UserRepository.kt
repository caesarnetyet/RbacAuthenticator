package com.rbac.data.repositories
import com.rbac.core.ISharedPreferencesService
import com.rbac.data.models.User
import com.rbac.datasources.remote.ApiResult
import com.rbac.datasources.remote.IRemoteUserDatasource


class UserRepository (
    private val remoteUserDatasource: IRemoteUserDatasource,
    private val sharedPreferences: ISharedPreferencesService
    ): IUserRepository {

    override suspend fun login(email: String, password: String): Result<String> {
        return when ( val result = remoteUserDatasource.login(email, password)) {
            is ApiResult.Success -> {
                sharedPreferences.saveString("token", result.data.token)
                Result.success(result.data.message)
            }

            is ApiResult.Error -> {
                Result.failure(Exception(result.errorResponse.message))
            }
        }

    }

    override suspend fun logout(): Result<Unit> {
        sharedPreferences.saveString("token", "")

        return when (val result = remoteUserDatasource.logout()) {
            is ApiResult.Success -> {
                Result.success(Unit)
            }

            is ApiResult.Error -> {
                Result.failure(Exception(result.errorResponse.message))
            }
        }
    }

    override suspend fun getAuthToken(code: String): Result<String> {
        return when (val response = remoteUserDatasource.fetchAuthToken(code)) {
            is ApiResult.Success -> {
                Result.success(response.data.token)
            }

            is ApiResult.Error -> {
                Result.failure(Exception(response.errorResponse.message))
            }
        }
    }

    override suspend fun getUser(): Result<User> {
        return when (val response = remoteUserDatasource.fetchUser()) {
            is ApiResult.Success -> {
                return Result.success(User(
                    response.data.id,
                    response.data.name,
                    response.data.email))
            }

            is ApiResult.Error -> {
                Result.failure(Exception(response.errorResponse.message))
            }
        }
    }

}