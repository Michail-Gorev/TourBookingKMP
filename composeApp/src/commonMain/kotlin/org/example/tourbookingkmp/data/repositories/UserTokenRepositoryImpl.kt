package org.example.tourbookingkmp.data.repositories

import org.example.tourbookingkmp.data.filehelpers.InternalStorageManager
import org.example.tourbookingkmp.domain.interfaces.repositories.UserTokenRepository
import org.example.tourbookingkmp.domain.models.User

class UserTokenRepositoryImpl : UserTokenRepository {

    override suspend fun getUserToken(user: User): String {
        return user.token
    }

    override suspend fun saveUserToken(token: String): String {
        val internalStorageManager = InternalStorageManager()
        val success = internalStorageManager
            .savePlainTextDataToFile(token, "userToken.txt")
        return success
    }

    override fun readUserTokenFromFile(): String {
        val internalStorageManager = InternalStorageManager()
        val token = internalStorageManager
            .readPlainTextDataFromFile("userToken.txt")
        return token
    }
}