package com.rbac.datasources.remote

import com.rbac.core.networking.IRbacApiService
import com.rbac.core.networking.RetrofitClient.Companion.parseErrorResponse
import com.rbac.core.networking.request.LoginRequest
import com.rbac.core.networking.request.TokenRequest
import com.rbac.core.networking.response.ErrorResponse
import com.rbac.core.networking.response.TokenResponse
import com.rbac.core.networking.response.UserResponse
import retrofit2.HttpException
import java.io.IOException

class RemoteUserDatasource(private val apiService: IRbacApiService): IRemoteUserDatasource {

    override suspend fun login(email: String, password: String): ApiResult<TokenResponse> {
        val request = LoginRequest(email, password)
        return  try {
                val response = apiService.login(request)
                ApiResult.Success(response)
            } catch (e: IOException) {
                ApiResult.Error(ErrorResponse("No hay conexión al sistema"))
            } catch (e: HttpException) {
                val errorResponse = parseErrorResponse(e.response()?.errorBody())
                ApiResult.Error(errorResponse ?: ErrorResponse("Error intentando iniciar sesión"))
        }
    }

    override suspend fun logout(): ApiResult<Unit> {
        return try {
                apiService.logout()
                ApiResult.Success(Unit)
            } catch (e: IOException) {
                ApiResult.Error(ErrorResponse("No hay conexión al sistema"))
            } catch (e: HttpException) {
                val errorResponse = parseErrorResponse(e.response()?.errorBody())
                ApiResult.Error(errorResponse ?: ErrorResponse("Error intentando cerrar sesión"))
            }
        }

    override suspend fun fetchAuthToken(code: String): ApiResult<TokenResponse> {
        val tokenRequest= TokenRequest(code)

        return try {
                val data = apiService.fetchAuthToken(tokenRequest)
                ApiResult.Success(data)
            } catch (e: IOException) {
                ApiResult.Error(ErrorResponse("No hay conexión al sistema"))
            } catch (e: HttpException) {
                val errorResponse = parseErrorResponse(e.response()?.errorBody())
                ApiResult.Error(errorResponse ?: ErrorResponse("Error intentando obtener token"))
            }
        }

    override suspend fun fetchUser(): ApiResult<UserResponse> {
        return try {
                val response = apiService.getUser()
                ApiResult.Success(response)
            } catch (e: IOException) {
                ApiResult.Error(ErrorResponse("No hay conexión al sistema"))
            } catch (e: HttpException) {
                val errorResponse = parseErrorResponse(e.response()?.errorBody())
                ApiResult.Error(errorResponse ?: ErrorResponse("Error intentando obtener usuario"))
            }
        }

}

