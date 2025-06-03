package org.example.tourbookingkmp

import android.os.Build
import org.example.tourbookingkmp.domain.TourRepository
import org.example.tourbookingkmp.models.Tour

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual suspend fun getAllTours(): List<Tour> {
    return TourRepository.getAllTours()
}