package shub39.icey.game

sealed interface GameAction {
    data class OnUseItem(val item: Item): GameAction
    data class OnPlayerShoot(val playerType: PlayerType): GameAction
    data object OnLoadShellsAndItems: GameAction
    data object OnReset: GameAction
//    data object On
}