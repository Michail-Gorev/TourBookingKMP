package org.example.tourbookingkmp.data.filehelpers

import android.content.Context
import java.io.File

actual class InternalStorageManager {

    actual suspend fun savePlainTextDataToFile(
        data: String,
        fileName: String
    ): String {
        try {
            val filesDir= appContext.filesDir
            val file = File(filesDir, fileName)
            file.writeText(data)
            return "Successfully saved plain text to file."
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error, occurred during saving, is above."
        }
    }

    actual fun readPlainTextDataFromFile(
        fileName: String
    ): String {
        try {
            val filesDir= appContext.filesDir
            val file = File(filesDir, fileName)
            val token = file.readText()
            return token
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    // Companion object для получения передаваемого в класс context-а
    companion object {
        private lateinit var appContext: Context

        fun init(context: Context) {
            appContext = context.applicationContext
        }
    }
}