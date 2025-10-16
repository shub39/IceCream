package shub39.icey.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import shub39.icey.game.GameState

class Statelayer {
    val gameState: MutableStateFlow<GameState> = MutableStateFlow(GameState())
}