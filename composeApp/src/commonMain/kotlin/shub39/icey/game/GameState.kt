package shub39.icey.game

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.Icecream
import androidx.compose.material.icons.rounded.Snowboarding
import androidx.compose.material.icons.rounded.Snowshoeing
import androidx.compose.material.icons.rounded.ZoomIn
import androidx.compose.ui.graphics.vector.ImageVector

data class GameState(
    val roundCounter: Int = 1,
    val gamePhase: GamePhase = GamePhase.Idle,
    val shells: List<ShellType> = listOf(),
    val playerHealth: Int = 5,
    val aiHealth: Int = 5,
    val playerItems: List<Item> = listOf(),
    val aiItems: List<Item> = listOf(),
    val message: String? = null,
    val skipPlayerTurn: Int = 0,
    val skipAiTurn: Int = 0,
    val doubleDamage: Boolean = false
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

enum class Item(val icon: ImageVector, val title: String) {
    Freeze(Icons.Rounded.Snowshoeing, "Steal Milky's next turn"),
    Glass(Icons.Rounded.ZoomIn, "Show next scoop"),
    Vanilla(Icons.Rounded.Icecream, "Heal yourself with vanilla icecream"),
    Chocolate(Icons.Rounded.Snowboarding, "Eject next scoop"),
    Lemon(Icons.Rounded.ElectricBolt, "Make the next scoop deal double freeze")
}