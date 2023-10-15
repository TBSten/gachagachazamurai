package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.mirrortunegame

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun NotesLine(
    vararg notes: Notes,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false,
    onClick: (notes: Notes) -> Unit,
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Notes.values().forEach {
            Box(Modifier.weight(1f)) {
                if (it in notes) {
                    Image(
                        painter = painterResource(if (placeholder) it.placeholderImage else it.image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                            .clickable(
                                enabled = true,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = false,
                                    color = Color(0xFFFFFFC0),
                                ),
                                onClick = { onClick(it) },
                            ),
                    )
                }
            }
        }
    }
}
