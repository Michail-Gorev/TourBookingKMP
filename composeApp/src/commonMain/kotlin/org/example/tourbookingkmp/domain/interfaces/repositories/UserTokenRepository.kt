package org.example.tourbookingkmp.domain.interfaces.repositories

import org.example.tourbookingkmp.domain.models.User

interface UserTokenRepository {
    suspend fun getUserToken(
        user: User
    ): String

    suspend fun saveUserToken(token: String): String
    fun readUserTokenFromFile(): String
}