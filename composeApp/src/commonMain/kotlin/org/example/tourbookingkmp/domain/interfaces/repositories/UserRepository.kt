package org.example.tourbookingkmp.domain.interfaces.repositories

import org.example.tourbookingkmp.domain.models.User

interface UserRepository {
    suspend fun getUser(email: String, password: String): User
}
