package org.example.tourbookingkmp.domain.interfaces.fileshelpers


expect suspend fun savePlainTextDataToFile(data: String, filePath: String): String
