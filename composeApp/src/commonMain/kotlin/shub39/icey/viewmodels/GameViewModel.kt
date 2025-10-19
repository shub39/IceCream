package shub39.icey.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import icecream.composeapp.generated.resources.Res
import icecream.composeapp.generated.resources.defeat
import icecream.composeapp.generated.resources.ejected_blank
import icecream.composeapp.generated.resources.ejected_scoop
import icecream.composeapp.generated.resources.milky_froozen
import icecream.composeapp.generated.resources.milky_threw_blank_scoop
import icecream.composeapp.generated.resources.milky_threw_scoop
import icecream.composeapp.generated.resources.milkys_turn
import icecream.composeapp.generated.resources.next_scoop_blank
import icecream.composeapp.generated.resources.next_scoop_peak
import icecream.composeapp.generated.resources.player_turn
import icecream.composeapp.generated.resources.see_the_tray
import icecream.composeapp.generated.resources.victory
import icecream.composeapp.generated.resources.you_shot_milky
import icecream.composeapp.generated.resources.you_shot_yourself
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
    stateLayer: Statelayer
) : ViewModel() {
    private val _state = stateLayer.gameState
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
                                    message = Res.string.next_scoop_blank,
                                    gamePhase = when(action.playerType) {
                                        PlayerType.Player -> GamePhase.PlayerTurn
                                        PlayerType.Ai -> GamePhase.AiTurn
                                    },
                                    doubleDamage = false
                                )
                            }

                            delay(2000)
                        }

                        ShellType.Live -> {
                            val handsawDamage = if (_state.value.doubleDamage) 1 else 0

                            when (action.playerType) {
                                PlayerType.Player -> {
                                    _state.update {
                                        it.copy(
                                            playerHealth = it.playerHealth - 1 - handsawDamage,
                                            doubleDamage = false,
                                            message = Res.string.you_shot_yourself
                                        )
                                    }
                                }
                                PlayerType.Ai -> {
                                    _state.update {
                                        it.copy(
                                            aiHealth = it.aiHealth - 1 - handsawDamage,
                                            doubleDamage = false,
                                            message = Res.string.you_shot_milky
                                        )
                                    }
                                }
                            }

                            delay(2000)

                            if (isGameOver()) return@launch

                            _state.update {
                                it.copy(gamePhase = GamePhase.AiTurn)
                            }
                        }
                    }

                    if (_state.value.shells.isEmpty()) {
                        loadShellsAndItems(_state.value.roundCounter + 1)
                        return@launch
                    }

                    if (_state.value.gamePhase == GamePhase.AiTurn) {
                        handleAiTurn()
                    }
                }

                is GameAction.OnUseItem -> {
                    when (action.item) {
                        Item.Freeze -> {
                            _state.update {
                                it.copy(
                                    skipAiTurn = 2
                                )
                            }
                        }

                        Item.Glass -> {
                            val nextShell = _state.value.shells.firstOrNull() ?: return@launch

                            _state.update {
                                it.copy(
                                    message = when (nextShell) {
                                        ShellType.Blank -> Res.string.next_scoop_blank
                                        ShellType.Live -> Res.string.next_scoop_peak
                                    }
                                )
                            }
                        }

                        Item.Vanilla -> {
                            _state.update {
                                it.copy(
                                    playerHealth = it.playerHealth + 1
                                )
                            }
                        }

                        Item.Chocolate -> {
                            val nextShell = _state.value.shells.firstOrNull() ?: return@launch

                            _state.update {
                                it.copy(
                                    shells = it.shells.toMutableList().apply {
                                        removeFirstOrNull()
                                    }.toList(),
                                    message = when (nextShell) {
                                        ShellType.Blank -> Res.string.ejected_blank
                                        ShellType.Live -> Res.string.ejected_scoop
                                    }
                                )
                            }
                        }

                        Item.Lemon -> {
                            _state.update {
                                it.copy(
                                    doubleDamage = true
                                )
                            }
                        }
                    }

                    _state.update {
                        it.copy(
                            playerItems = it.playerItems.toMutableList().apply {
                                remove(action.item)
                            }.toList()
                        )
                    }

                    if (_state.value.shells.isEmpty()) loadShellsAndItems(_state.value.roundCounter + 1)
                }

                GameAction.OnLoadShellsAndItems -> loadShellsAndItems()

                GameAction.OnReset -> _state.update { GameState() }
            }
        }
    }

    private suspend fun handleAiTurn() {
        if (_state.value.skipAiTurn == 2) {
            _state.update { it.copy(message = Res.string.milky_froozen) }
            delay(2000)
            _state.update {
                it.copy(
                    gamePhase = GamePhase.PlayerTurn,
                    skipAiTurn = it.skipAiTurn - 1,
                    message = Res.string.player_turn
                )
            }
            return
        }

        _state.update { it.copy(message = Res.string.milkys_turn) }
        delay(2000)

        val currentShell = _state.value.shells.firstOrNull() ?: return

        when (currentShell) {
            ShellType.Blank -> {
                _state.update { it.copy(message = Res.string.milky_threw_blank_scoop) }
            }
            ShellType.Live -> {
                _state.update {
                    it.copy(
                        playerHealth = it.playerHealth - 1,
                        message = Res.string.milky_threw_scoop
                    )
                }
            }
        }

        delay(2000)

        if (isGameOver()) return

        _state.update {
            it.copy(
                shells = it.shells.drop(1),
                gamePhase = GamePhase.PlayerTurn,
                skipAiTurn = it.skipAiTurn - 1,
                message = Res.string.player_turn
            )
        }

        if (_state.value.shells.isEmpty()) {
            loadShellsAndItems(_state.value.roundCounter + 1)
        }
    }

    private fun isGameOver(): Boolean {
        return when {
            _state.value.playerHealth <= 0 -> {
                _state.update {
                    it.copy(
                        gamePhase = GamePhase.Defeat,
                        message = Res.string.defeat
                    )
                }

                true
            }

            _state.value.aiHealth <= 0 -> {
                _state.update {
                    it.copy(
                        gamePhase = GamePhase.Victory,
                        message = Res.string.victory
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

        _state.update {
            it.copy(
                shells = shells.shuffled(Random),
                gamePhase = GamePhase.ShowShells,
                message = Res.string.see_the_tray,
                skipAiTurn = 0,
                skipPlayerTurn = 0,
                doubleDamage = false,
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
                message = Res.string.player_turn,
                roundCounter = roundNo
            )
        }
    }
}