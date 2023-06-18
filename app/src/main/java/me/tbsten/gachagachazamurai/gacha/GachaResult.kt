package me.tbsten.gachagachazamurai.gacha

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import me.tbsten.gachagachazamurai.R
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.ui.theme.GachaGachaZamuraiTheme

@Composable
fun GachaResult(
    prizeItem: PrizeItem,
    actions: (@Composable () -> Unit)? = null,
) {
    val showImage = prizeItem.image != null

    ConstraintLayout {
        val (image, surface) = createRefs()

        if (showImage) {
            GachaResultImage(
                modifier = Modifier
                    .constrainAs(image) {
                        val margin = 32.dp
                        start.linkTo(parent.start, margin)
                        end.linkTo(parent.end, margin)
                        width = fillToConstraints
                    },
                prizeItem = prizeItem,
            )
        }

        CardContent(
            modifier = Modifier
                .constrainAs(surface) {
                    if (showImage) top.linkTo(image.bottom, margin = (-12).dp)
                    else top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            prizeItem = prizeItem,
            actions = actions,
        )

    }

}

@Composable
private fun GachaResultImage(
    modifier: Modifier = Modifier,
    prizeItem: PrizeItem,
) {
    val painter = painterResource(R.drawable.capsule_bottom)
    Image(
        painter = painter,
        contentDescription = prizeItem.name,
        modifier = modifier
            .aspectRatio(painter.intrinsicSize.let { it.width / it.height })
            .zIndex(1f),
    )
}

@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    prizeItem: PrizeItem,
    actions: (@Composable () -> Unit)? = null,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 12.dp,
                end = 12.dp,
                bottom = 12.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                prizeItem.name,
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                prizeItem.detail,
                style = MaterialTheme.typography.bodyMedium.also {
                    it.copy(
                        color = it.color.copy(alpha = 0.5f)
                    )
                })

            actions?.let {
                Row(
                    modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    it.invoke()
                }
            }

        }
    }
}

@Preview(widthDp = 300)
@Composable
fun GachaResultPreview() {
    GachaGachaZamuraiTheme {
        GachaResult(
            prizeItem = PrizeItem(
                id = 0,
                name = "テストアイテム",
                detail = "テスト用の景品".repeat(10),
                stock = 10,
                purchase = 10,
                image = "",
                rarity = PrizeItem.Rarity.NORMAL,
            ),
            actions = {
                TextButton(onClick = {}) { Text("戻る") }
                Button(onClick = {}) { Text("もう一度") }
            }
        )
    }
}
