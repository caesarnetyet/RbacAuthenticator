package com.rbac.core

interface ISharedPreferencesService {
    fun saveString(key: String, value: String)
    fun getString(key: String): String?
}