package org.example.tourbookingkmp.data.filehelpers

import android.content.Context
import kotlinx.coroutines.currentCoroutineContext
import org.example.tourbookingkmp.presenter.MainActivity
import java.io.File

// TODO доделать использование context вместо захардкоженного пути до файловой директории
actual class InternalStorageManager actual constructor () {
//    private val context: Context
//        get() = MainActivity().baseContext
    actual suspend fun savePlainTextDataToFile(
        data: String,
        fileName: String
    ): String {
        try {
            val filesDir= "/data/data/org.example.tourbookingkmp/files"
            val file = File(filesDir, fileName)
            file.writeText(data)
            return "Successfully saved plain text to file."
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error, occurred during saving, is above."
        }
    }
}