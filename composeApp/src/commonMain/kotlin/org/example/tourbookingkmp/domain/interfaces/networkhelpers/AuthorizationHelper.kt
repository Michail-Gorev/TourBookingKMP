package org.example.tourbookingkmp.domain.interfaces.networkhelpers

import org.example.tourbookingkmp.LoginUserMutation
import org.example.tourbookingkmp.domain.models.User

interface AuthorizationHelper {
    suspend fun register(user: User): String
    suspend fun getUserWithLogin(email: String, password: String): LoginUserMutation.Login
}