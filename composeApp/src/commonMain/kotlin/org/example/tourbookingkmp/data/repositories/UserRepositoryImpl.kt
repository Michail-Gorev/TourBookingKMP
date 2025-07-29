package org.example.tourbookingkmp.data.repositories

import org.example.tourbookingkmp.domain.interfaces.networkhelpers.AuthorizationHelper
import org.example.tourbookingkmp.domain.interfaces.repositories.UserRepository
import org.example.tourbookingkmp.domain.models.Order
import org.example.tourbookingkmp.domain.models.User

class UserRepositoryImpl(
    private val authorizationHelper: AuthorizationHelper
) : UserRepository {
    override suspend fun getUser(
        email: String,
        password: String
    ): User {
        val userData = authorizationHelper.getUserWithLogin(email, password)
        val user = User(
            userData.token,
            userData.user.id,
            userData.user.firstName,
            userData.user.lastName,
            email = userData.user.email,
            orders = userData.user.orders?.map {
                Order(
                    id = it?.id ?: "-1"
                )
            } ?: emptyList()
        )
        return user
    }
}