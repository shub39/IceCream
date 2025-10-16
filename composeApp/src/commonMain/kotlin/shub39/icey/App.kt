package shub39.icey

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import shub39.icey.game.GameScreen
import shub39.icey.viewmodels.GameViewModel

@Composable
fun App() {
    MaterialTheme {
        val viewModel = koinViewModel<GameViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        GameScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}