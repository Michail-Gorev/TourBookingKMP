package org.example.tourbookingkmp.domain.usecases

import org.example.tourbookingkmp.domain.interfaces.repositories.UserRepository
import org.example.tourbookingkmp.domain.interfaces.repositories.UserTokenRepository
import org.example.tourbookingkmp.domain.models.User

class LoginUserByEmailUseCase(
    private val userRepository: UserRepository,
    private val userTokenRepository: UserTokenRepository
) {
    suspend fun invoke(
        email: String,
        password: String
    ): User {
        val user = userRepository.getUser(email, password)
        val token = userTokenRepository.getUserToken(user)
        userTokenRepository.saveUserToken(token)
        return user
    }
}