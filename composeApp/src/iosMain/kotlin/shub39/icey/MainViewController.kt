package shub39.icey

import androidx.compose.ui.window.ComposeUIViewController
import shub39.icey.di.initKoin

fun MainViewController() {
    initKoin()

    ComposeUIViewController { App() }
}