package shub39.icey

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import shub39.icey.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "IceCream",
        ) {
            App()
        }
    }
}