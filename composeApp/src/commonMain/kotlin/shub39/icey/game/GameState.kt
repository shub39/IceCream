package shub39.icey.game

data class GameState(
    val roundCounter: Int = 1,
    val gamePhase: GamePhase = GamePhase.Idle,
    val shells: List<ShellType> = listOf(),
    val playerHealth: Int = 5,
    val aiHealth: Int = 5,
    val playerItems: List<Item> = listOf(),
    val aiItems: List<Item> = listOf(),
    val message: String? = null
)

enum class PlayerType {
    Player, Ai
}

enum class GamePhase {
    Idle,
    Victory,
    Defeat,
    PlayerTurn,
    AiTurn,
    ShowShells
}

enum class ShellType {
    Blank,
    Live
}

enum class Item {
    HandCuff,
    MagnifyingGlass,
    Cigarette,
    Beer
}