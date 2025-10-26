package de.madtracki.transaktor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform