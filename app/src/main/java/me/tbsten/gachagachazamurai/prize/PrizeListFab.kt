package me.tbsten.gachagachazamurai.prize

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun PrizeListFab(
    onClick: () -> Unit,
) {
    FloatingActionButton(onClick) {
        Icon(Icons.Default.Add, contentDescription = "add new prize")
    }
}
