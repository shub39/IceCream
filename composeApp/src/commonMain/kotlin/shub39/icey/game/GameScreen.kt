package shub39.icey.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import shub39.icey.game.ui.components.HealthStats

@OptIn(ExperimentalMaterial3Api::class)
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

        if (state.gamePhase != GamePhase.Idle) {
            Card(shape = MaterialTheme.shapes.extraLarge) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .animateContentSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = state.message ?: "...",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            textAlign = TextAlign.Center
                        )
                    )

                    AnimatedVisibility(visible = state.gamePhase == GamePhase.ShowShells) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.shells.forEach { shell ->
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(
                                            shape = CircleShape,
                                            color = when (shell) {
                                                ShellType.Blank -> MaterialTheme.colorScheme.primary
                                                ShellType.Live -> MaterialTheme.colorScheme.background
                                            }
                                        )
                                )
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

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(visible = state.gamePhase != GamePhase.Idle && (state.playerItems.isNotEmpty()) || state.gamePhase == GamePhase.PlayerTurn) {
                Card(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (state.gamePhase == GamePhase.PlayerTurn) {
                            Row(
                                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
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

                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.playerItems.forEach { item ->
                                TooltipBox(
                                    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                                    tooltip = {
                                        PlainTooltip {
                                            Text(text = item.title)
                                        }
                                    },
                                    state = rememberTooltipState()
                                ) {
                                    FilledTonalIconButton(
                                        onClick = { onAction(GameAction.OnUseItem(item)) },
                                        enabled = state.gamePhase == GamePhase.PlayerTurn &&
                                                when {
                                                    state.doubleDamage && item == Item.Lemon -> false
                                                    state.skipAiTurn > 0 && item == Item.Freeze -> false
                                                    else -> true
                                                }
                                    ) {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

