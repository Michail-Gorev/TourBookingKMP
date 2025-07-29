package org.example.tourbookingkmp.data.networkhelpers

import org.example.tourbookingkmp.LoginUserMutation
import org.example.tourbookingkmp.RegisterUserMutation
import org.example.tourbookingkmp.data.apolloClient
import org.example.tourbookingkmp.domain.interfaces.networkhelpers.AuthorizationHelper
import org.example.tourbookingkmp.domain.models.User
import org.example.tourbookingkmp.type.LoginInput
import org.example.tourbookingkmp.type.UserInput


class AuthorizationHelperImpl : AuthorizationHelper {
    private fun mapUserToUserInput(
        user: User
    ): UserInput {
        val firstName = user.firstName
        val lastName = user.lastName
        val email = user.email
        val password = user.password
        val userInput = UserInput(firstName, lastName, email, password)
        return userInput
    }

    override suspend fun register(
        user: User
    ): String {
        val userInput = mapUserToUserInput(user)
        val response = apolloClient.mutation(RegisterUserMutation(userInput)).execute()
        println(response)
        return response.data?.createUser?.token ?: ""
    }

    override suspend fun getUserWithLogin(
        email: String,
        password: String
    ): LoginUserMutation.Login {
        val loginInput = LoginInput(email, password)
        val response = apolloClient.mutation(LoginUserMutation(loginInput)).execute()
        return response.data?.login ?: LoginUserMutation.Login(
            "",
            LoginUserMutation.User(
                "-1",
                "lost",
                "user",
                "qq@qq.qq",
                emptyList()
            )
        )
    }
}