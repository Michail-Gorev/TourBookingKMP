package org.example.tourbookingkmp.data.repositories

import org.example.tourbookingkmp.domain.interfaces.repositories.UserTokenRepository
import org.example.tourbookingkmp.domain.models.User

class UserTokenRepositoryImpl: UserTokenRepository {

    override suspend fun getUserToken(user: User): String {
        return user.token
    }

    override suspend fun saveUserToken(token: String): String {
        println("token: $token")
        return ""
    }
}