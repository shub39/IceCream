package shub39.icey

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform