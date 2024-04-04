package com.rbac.rbacauthenticator

import com.rbac.core.SharedPreferencesService
import com.rbac.core.networking.RetrofitClient
import com.rbac.data.repositories.IUserRepository
import com.rbac.data.repositories.UserRepository
import com.rbac.datasources.remote.RemoteUserDatasource

class AppContainer {
    private val sharedPreferences = SharedPreferencesService(App.appContext)
    private val retrofitClient = RetrofitClient(sharedPreferences).instance
    private val remoteUserDatasource = RemoteUserDatasource(retrofitClient)
    private val userRepository = UserRepository(remoteUserDatasource, sharedPreferences)

    fun provideUserRepository(): IUserRepository {
        return userRepository
    }

}