package shub39.icey.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shub39.icey.game.GameAction
import shub39.icey.game.GamePhase
import shub39.icey.game.GameState
import shub39.icey.game.Item
import shub39.icey.game.PlayerType
import shub39.icey.game.ShellType
import kotlin.random.Random

class GameViewModel(
    private val statelayer: Statelayer
) : ViewModel() {
    private val _state = statelayer.gameState
    val state = _state.asStateFlow()
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    fun onAction(action: GameAction) {
        viewModelScope.launch {
            when (action) {
                is GameAction.OnPlayerShoot -> {
                    val currentShell = _state.value.shells.firstOrNull() ?: return@launch

                    _state.update {
                        it.copy(
                            shells = it.shells.toMutableList().apply {
                                removeFirstOrNull()
                            }.toList()
                        )
                    }

                    when (currentShell) {
                        ShellType.Blank -> {
                            _state.update {
                                it.copy(
                                    message = "Click!! shell was blank...",
                                    gamePhase = when(action.playerType) {
                                        PlayerType.Player -> GamePhase.PlayerTurn
                                        PlayerType.Ai -> GamePhase.AiTurn
                                    }
                                )
                            }
                        }

                        ShellType.Live -> {
                            when (action.playerType) {
                                PlayerType.Player -> {
                                    _state.update {
                                        it.copy(
                                            playerHealth = it.playerHealth - 1
                                        )
                                    }
                                }
                                PlayerType.Ai -> {
                                    _state.update {
                                        it.copy(
                                            aiHealth = it.aiHealth - 1
                                        )
                                    }
                                }
                            }

                            if (isGameOver()) return@launch

                            _state.update { it.copy(gamePhase = GamePhase.AiTurn) }
                        }
                    }

                    if (_state.value.shells.isEmpty()) {
                        loadShellsAndItems(_state.value.roundCounter + 1)
                        return@launch
                    }

                    if (_state.value.gamePhase == GamePhase.AiTurn) {
                        _state.update { it.copy(message = "Ai's Turn") }

                        delay(1000)

                        val currentShell = _state.value.shells.firstOrNull() ?: return@launch

                        when (currentShell) {
                            ShellType.Blank -> {
                                _state.update {
                                    it.copy(
                                        message = "Ai clicked!! shell was blank..."
                                    )
                                }
                            }
                            ShellType.Live -> {
                                _state.update {
                                    it.copy(
                                        playerHealth = it.playerHealth - 1,
                                        message = "You were shot!!"
                                    )
                                }
                            }
                        }

                        delay(1000)

                        if (isGameOver()) return@launch

                        _state.update {
                            it.copy(
                                shells = it.shells.toMutableList().apply {
                                    removeFirstOrNull()
                                }.toList(),
                                gamePhase = GamePhase.PlayerTurn,
                                message = "Your Turn"
                            )
                        }

                        if (_state.value.shells.isEmpty()) {
                            loadShellsAndItems(_state.value.roundCounter + 1)
                            return@launch
                        }
                    }
                }

                is GameAction.OnUseItem -> {}

                GameAction.OnLoadShellsAndItems -> loadShellsAndItems()

                GameAction.OnReset -> _state.update { GameState() }
            }
        }
    }

    private fun isGameOver(): Boolean {
        return when {
            _state.value.playerHealth <= 0 -> {
                _state.update {
                    it.copy(
                        gamePhase = GamePhase.Defeat,
                        message = "You Lost!"
                    )
                }

                true
            }

            _state.value.aiHealth <= 0 -> {
                _state.update {
                    it.copy(
                        gamePhase = GamePhase.Victory,
                        message = "You Won!"
                    )
                }

                true
            }

            else -> false
        }
    }

    private suspend fun loadShellsAndItems(roundNo: Int = 1) {
        var shells = (0..7).map { ShellType.entries.random() }
        if (!shells.contains(ShellType.Live)) {
            shells = shells.toMutableList().apply {
                removeAt(0)
                add(0, ShellType.Live)
            }.toList().shuffled(Random)
        }
        val blankShells = shells.count { it == ShellType.Blank }
        val liveShells = shells.count { it == ShellType.Live }

        _state.update {
            it.copy(
                shells = shells.shuffled(Random),
                gamePhase = GamePhase.ShowShells,
                message = "Live Shells: $liveShells, Blank Shells: $blankShells"
            )
        }

        delay(3000)

        val playerItems = (0..3).map { Item.entries.random() }
        val aiItems = (0..3).map { Item.entries.random() }

        _state.update {
            it.copy(
                playerItems = playerItems,
                aiItems = aiItems,
                gamePhase = GamePhase.PlayerTurn,
                message = "Your Turn",
                roundCounter = roundNo
            )
        }
    }
}