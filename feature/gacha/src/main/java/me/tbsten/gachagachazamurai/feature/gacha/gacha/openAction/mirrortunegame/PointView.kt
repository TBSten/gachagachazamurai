package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.mirrortunegame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun PointView(
    point: Int,
    modifier: Modifier = Modifier,
    size: PointViewSize = PointViewSize.BASE,
) {
    Box(
        modifier
            .padding(16.dp)
    ) {
        Text(
            text = "$point pt",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = size.fontSize,
        )
    }

}

enum class PointViewSize(
    val fontSize: TextUnit,
) {
    BASE(36.sp),
    LG(48.sp),
}
