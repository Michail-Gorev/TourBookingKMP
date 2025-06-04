package org.example.tourbookingkmp

import org.example.tourbookingkmp.models.Tour
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
actual suspend fun getAllTours(): List<Tour> {
    val response = apolloClient.query(GetAllToursQuery()).execute()
    return response.data?.tours?.map {
        Tour(
            id = it.id ?: 1,
            title = it.title ?: "Untitled",
            price = it.price ?: 0.0,
            city = it.city ?: "Unknown",
            description = "TODO()",
            transfer = true,
            isActive = true
        )
    } ?: emptyList()
}