package me.tbsten.gachagachazamurai.prize.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.tbsten.gachagachazamurai.component.applyIf
import me.tbsten.gachagachazamurai.domain.PrizeItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PrizeListItem(
    prizeItem: PrizeItem,
    onDoubleClick: (() -> Unit)? = null,
    showDetail: Boolean = false,
    modifier: Modifier = Modifier,
) {
    with(prizeItem) {
        Column(
            modifier = modifier
                .applyIf(onDoubleClick != null) {
                    combinedClickable(
                        onClick = {},
                        onDoubleClick = onDoubleClick,
                    )
                }
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(name, modifier = Modifier.weight(1f))
                Text(
                    text = rarity.displayName,
                    fontSize = 12.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.7f
                    ),
                )
                Spacer(Modifier.size(16.dp))
                Text(
                    text = "$stock / $purchase",
                )
            }
            if (showDetail) {
                Box(Modifier.padding(start = 16.dp).fillMaxWidth()) {
                    Text(
                        detail,
                        style = LocalTextStyle.current.let { it.copy(fontSize = it.fontSize * 0.75) },
                    )
                }
            }
        }

    }
}
