package org.example.tourbookingkmp

import org.example.tourbookingkmp.models.Tour

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect suspend fun getAllTours(): List<Tour>