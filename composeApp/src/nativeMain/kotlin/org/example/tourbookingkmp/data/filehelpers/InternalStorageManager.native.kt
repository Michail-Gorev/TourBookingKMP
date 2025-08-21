package org.example.tourbookingkmp.data.filehelpers

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

actual class InternalStorageManager {

    private fun getDocumentsDirectory(): String {
        val paths = NSSearchPathForDirectoriesInDomains(
            directory = NSDocumentDirectory,
            domainMask = NSUserDomainMask,
            expandTilde = true
        )
        return paths.first() as String
    }

    private fun getFilePath(fileName: String): String {
        return getDocumentsDirectory() + "/$fileName"
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    actual suspend fun savePlainTextDataToFile(data: String, fileName: String): String {
        val filePath = getFilePath(fileName)
        try {
            val nsString = NSString.create(string = data)
            nsString.writeToFile(
                filePath,
                atomically = true,
                encoding = NSUTF8StringEncoding,
                error = null
            )
            return "Successfully saved plain text to file."
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error, occurred during saving, is above."
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun readPlainTextDataFromFile(fileName: String): String {
        val filePath = getFilePath(fileName)
        try {
            val content = NSString.stringWithContentsOfFile(
                path = filePath,
                encoding = NSUTF8StringEncoding,
                error = null
            )
            return content ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error, occurred during reading text data, is above."
        }
    }
}
