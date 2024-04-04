package com.rbac.data.datasources.remote.dto

data class UserDTO(
    val id: String,
    val name: String,
    val email: String,
    val roles: List<Role>
)

data class Role(
    val id: String,
    val name: String
)
