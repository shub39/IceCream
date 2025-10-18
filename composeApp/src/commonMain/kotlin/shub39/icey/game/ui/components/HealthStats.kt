package shub39.icey.game.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import shub39.icey.game.GameState

@Composable
fun BoxScope.HealthStats(state: GameState) {
    Card(
        modifier = Modifier
            .animateContentSize()
            .align(Alignment.CenterStart),
        shape = RoundedCornerShape(
            topEnd = 16.dp,
            bottomEnd = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (1..state.aiHealth).forEach { _ ->
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null
                )
            }

            if (state.aiHealth <= 0) {
                Icon(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    }
    Card(
        modifier = Modifier
            .animateContentSize()
            .align(Alignment.CenterEnd),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            bottomStart = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (1..state.playerHealth).forEach { _ ->
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null
                )
            }

            if (state.playerHealth <= 0) {
                Icon(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    }
}