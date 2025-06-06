package org.example.tourbookingkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform