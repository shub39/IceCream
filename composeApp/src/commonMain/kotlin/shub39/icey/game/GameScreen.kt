package shub39.icey.game

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import shub39.icey.game.ui.components.HealthStats

@Composable
fun GameScreen(
    state: GameState,
    onAction: (GameAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "IceCream Roulette",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "Round ${state.roundCounter}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            )
        }

        HealthStats(state = state)

        if (state.gamePhase == GamePhase.Idle) {
            FilledTonalButton(
                onClick = { onAction(GameAction.OnLoadShellsAndItems) },
                modifier = Modifier.align(Alignment.Center),
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "null"
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(text = "Start Game")
            }
        }

        Card {
            Text(
                text = state.message ?: ""
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
                        enabled = state.gamePhase == GamePhase.PlayerTurn &&
                            when {
                                state.doubleDamage && item == Item.HandSaw -> false
                                state.skipTurn == PlayerType.Ai && item == Item.HandCuff -> false
                                else -> true
                            }
                    ) {
                        Text(text = item.name)
                    }
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

