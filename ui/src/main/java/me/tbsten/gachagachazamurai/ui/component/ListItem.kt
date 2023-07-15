package me.tbsten.gachagachazamurai.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItem(
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Row(
        Modifier
            .combinedClickable(
                onClick = { onClick?.invoke() },
                onLongClick = { onLongClick?.invoke() },
            )
            .fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        content()
    }
}

@Preview
@Composable
fun ListItemPreview() {
    ListItem { Text("ListItem-Preview-1") }
}

@Preview
@Composable
fun ClickableListItemPreview() {
    ListItem(onClick = {}) { Text("ListItem-Preview-1") }
}
