package com.rbac.data.repositories

import com.rbac.core.ISharedPreferencesService
import com.rbac.core.networking.RetrofitClient
import com.rbac.datasources.remote.RemoteUserDatasource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MockStorage: ISharedPreferencesService {
    private val storage = mutableMapOf<String, String>()

    override fun saveString(key: String, value: String) {
        storage[key] = value
    }

    override fun getString(key: String): String? {
        return storage[key]
    }

}

class UserRepositoryLoginUnitTest {
    private lateinit var userRepository: UserRepository
    private lateinit var mockStorage: MockStorage

    @Before
    fun setup() {
        mockStorage = MockStorage()
        val network = RetrofitClient(mockStorage)
        val service = RemoteUserDatasource(network.instance)
        userRepository = UserRepository(service, mockStorage)
    }

    @Test
    fun can_login_user() = runBlocking {
        val email = "tovarjuliocesar779@gmail.com"
        val password = "password"

        val result = userRepository.login(email, password)
        assert(result.isSuccess)
        val token = mockStorage.getString("token")
        assert(token != null)
    }

    @Test
    fun can_return_error_on_login() = runBlocking {
        val email = "tasdasda@!dfsaas.com"
        val password = "password"

       val result = userRepository.login(email, password)

        assert(result.exceptionOrNull()?.message == "Error intentando iniciar sesión")
    }

    @Test
    fun cant_logout_user() = runBlocking {
        val result = userRepository.logout()

        assert(result.isFailure)

        assert(result.exceptionOrNull()?.message == "Error intentando cerrar sesión")

        val token = mockStorage.getString("token")
        assert(token == "")
    }

    @Test
    fun can_get_user() = runBlocking {
        val email = "tovarjuliocesar779@gmail.com"
        val password = "password"
        val result = userRepository.login(email, password)
        assert(result.isSuccess)

        val user = userRepository.getUser()

        assert(user.isSuccess)

        assertNotNull(user.getOrNull())
    }

    @Test
    fun can_generate_token() = runBlocking {
        val email = "tovarjuliocesar779@gmail.com"
        val password = "password"
        val result = userRepository.login(email, password)
        assert(result.isSuccess)
        val phoneToken =  "29b6bb2d"

        val token = userRepository.getAuthToken(phoneToken)
        assert(token.isSuccess)
    }

}