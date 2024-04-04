package com.rbac.core.networking.request

data class LoginRequest(
    val email: String,
    val password: String
)
