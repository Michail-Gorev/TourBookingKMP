package org.example.tourbookingkmp.domain.usecases

import org.example.tourbookingkmp.domain.interfaces.networkhelpers.AuthorizationHelper
import org.example.tourbookingkmp.domain.models.User

class RegisterUserByEmailUseCase(
    private val authHelper: AuthorizationHelper
) {
    suspend fun invoke(user: User): String {
        val token: String = authHelper.register(user)
        return token
    }
}