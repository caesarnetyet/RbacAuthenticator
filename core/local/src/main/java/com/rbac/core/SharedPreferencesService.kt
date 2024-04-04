package com.rbac.core

import android.content.Context

class SharedPreferencesService(context: Context): ISharedPreferencesService {
    private val sharedPreferences = context.getSharedPreferences("app", Context.MODE_PRIVATE)

    override fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}