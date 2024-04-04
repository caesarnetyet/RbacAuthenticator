package com.rbac.core.networking

import com.rbac.core.ISharedPreferencesService
import com.rbac.core.networking.response.ErrorResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient(private val sharedPreferencesService: ISharedPreferencesService) {
    private val baseURL = "http://localhost:8000/api/"

    private val authInterceptor = Interceptor { chain ->
        val token = sharedPreferencesService.getString("token") ?: ""
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        chain.proceed(newRequest)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel((HttpLoggingInterceptor.Level.BODY)))
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    val instance: IRbacApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(IRbacApiService::class.java)
    }

    companion object {
        fun parseErrorResponse(errorBody: ResponseBody?): ErrorResponse? {
            errorBody?.let {
                return try {
                    val moshi = Moshi.Builder().build()
                    val jsonAdapter = moshi.adapter(ErrorResponse::class.java)
                    jsonAdapter.fromJson(it.string())
                } catch (e: Exception) {
                    null
                }
            }
            return null
        }
    }

}