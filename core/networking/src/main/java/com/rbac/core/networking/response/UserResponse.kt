package com.rbac.core.networking.response

data class UserResponse (
    val id: Int,
    val name: String,
    val email: String,
    val roles: List<Role>
)

data class Role(
    val id: Int,
    val name: String
)