package shub39.icey.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Just to visualize the logic for now...
@Composable
fun GameScreen(
    state: GameState,
    onAction: (GameAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card {
            Text(
                text = "Round ${state.roundCounter}"
            )

            Text(
                text = state.message ?: "Ice Cream"
            )

            Text(
                text = "Ai Health: ${state.aiHealth}"
            )
            Text(
                text = "Your Health: ${state.playerHealth}"
            )

            Row {
                state.aiItems.forEach { item ->
                    Button(
                        onClick = {},
                        enabled = false
                    ) {
                        Text(text = item.name)
                    }
                }
            }

            if (state.gamePhase == GamePhase.ShowShells) {
                Row {
                    state.shells.forEach { shellType ->
                        Text(
                            when (shellType) {
                                ShellType.Blank -> "0"
                                ShellType.Live -> "1"
                            }
                        )
                    }
                }
            }

            Row {
                state.playerItems.forEach { item ->
                    Button(
                        onClick = { onAction(GameAction.OnUseItem(item)) },
                        enabled = if (item != Item.HandCuff) {
                            state.gamePhase == GamePhase.PlayerTurn
                        } else {
                            state.gamePhase == GamePhase.PlayerTurn && state.skipTurn == null
                        }
                    ) {
                        Text(text = item.name)
                    }
                }
            }

            if (state.gamePhase == GamePhase.Idle) {
                Button(
                    onClick = { onAction(GameAction.OnLoadShellsAndItems) }
                ) {
                    Text(text = "Load Shells")
                }
            }

            if (state.gamePhase == GamePhase.PlayerTurn) {
                Row {
                    Button(
                        onClick = { onAction(GameAction.OnPlayerShoot(PlayerType.Ai)) }
                    ) {
                        Text(text = "Shoot Ai")
                    }
                    Button(
                        onClick = { onAction(GameAction.OnPlayerShoot(PlayerType.Player)) }
                    ) {
                        Text(text = "Shoot Yourself")
                    }
                }
            }

            if (state.gamePhase == GamePhase.Defeat || state.gamePhase == GamePhase.Victory) {
                Button(
                    onClick = { onAction(GameAction.OnReset) }
                ) {
                    Text(text = "Reset")
                }
            }
        }
    }
}