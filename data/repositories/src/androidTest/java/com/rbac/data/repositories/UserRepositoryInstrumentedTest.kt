package com.rbac.data.repositories

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rbac.core.SharedPreferencesService
import com.rbac.core.networking.RetrofitClient
import com.rbac.datasources.remote.RemoteUserDatasource
import com.rbac.rbacauthenticator.App
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRepositoryInstrumentedTest {
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        val context = App.appContext
        val retrofit = RetrofitClient.instance
        val remoteDatasource = RemoteUserDatasource(retrofit)
        val sharedPreferencesService = SharedPreferencesService(context)

        userRepository = UserRepository(remoteDatasource, sharedPreferencesService)
    }

    @Test
    fun login_and_retrieve_user() = runBlocking {
        // Given
        val email = "tovarjuliocesar779@gmail.com"
        val password = "password"

        val result = userRepository.login(email, password)

        assert(result.isSuccess)
    }


}
