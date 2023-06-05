package me.tbsten.gachagachazamurai.prize.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun PrizeListFab(
    onClick: () -> Unit,
) {
    FloatingActionButton(onClick) {
        Icon(Icons.Default.Edit, contentDescription = "add new prize")
    }
}
