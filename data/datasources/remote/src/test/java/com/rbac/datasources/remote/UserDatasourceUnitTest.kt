package com.rbac.datasources.remote

import com.rbac.core.ISharedPreferencesService
import com.rbac.core.networking.RetrofitClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MockSharedPreferencesService() : ISharedPreferencesService {
    private val storageMap = mutableMapOf<String, String>()

    override fun saveString(key: String, value: String) {
        storageMap[key] = value

    }

    override fun getString(key: String): String? {
        return storageMap[key]
    }

}

class UserDatasourceUnitTest {
    private lateinit var apiService: RemoteUserDatasource
    private lateinit var mockSharedPreferencesService: MockSharedPreferencesService

    @Before
    fun setup() {
        mockSharedPreferencesService = MockSharedPreferencesService()
        val rbacApiService = RetrofitClient(mockSharedPreferencesService)
        apiService = RemoteUserDatasource(rbacApiService.instance)
    }

    @Test
    fun can_run_login_user() = runBlocking {
        val email = "tovarjuliocesar779@gmail.com"
        val password = "password"
        when (val response = apiService.login(email, password)) {
            is ApiResult.Success -> {
                val token: String = response.data.token
                assertNotNull(token)
            }
            else -> {
                assert(false)

            }
        }
    }

    @Test
    fun can_return_error_on_login() = runBlocking {
        val email = "tasdasda@!dfsaas.com"
        val password = "password"
        when (val response = apiService.login(email, password)) {
            is ApiResult.Error -> {
                assertNotNull(response)
                assert(response.errorResponse.message == "Error intentando iniciar sesión")
            }
            else -> {
                assert(false)
            }
        }
    }

    @Test
    fun can_get_user() = runBlocking {
        val email = "tovarjuliocesar779@gmail.com"
        val password = "password"
        when (val response = apiService.login(email, password)) {
            is ApiResult.Success -> {
                mockSharedPreferencesService.saveString("token", response.data.token)
                when (val userResponse = apiService.fetchUser()) {
                    is ApiResult.Success -> {
                        assertNotNull(userResponse.data)
                    }
                    else -> {
                        assert(false)
                    }
                }
            }
            else -> {
                assert(false)
            }
        }
    }
}
