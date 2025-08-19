package org.example.tourbookingkmp.data.filehelpers

expect class InternalStorageManager () {
    suspend fun savePlainTextDataToFile(
        data: String,
        fileName: String
    ): String
}