package me.tbsten.gachagachazamurai.feature.gacha.prizeList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.feature.gacha.R

@Composable
fun PrizeListScreen(
    gotoGachaPlay: () -> Unit,
    prizeListViewModel: PrizeListViewModel = hiltViewModel(),
) {
    val prizeItems = prizeListViewModel.prizeItems.collectAsState().value

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { gotoGachaPlay() },
            ) {
                Icon(Icons.Default.ArrowForwardIos, contentDescription = "ガチャを引く")
                Text("ガチャを引く")
            }
        },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            // TODO バナー

            Text(
                text = "景品",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 24.dp),
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
            )

            val gridCells = 3
            LazyVerticalGrid(
                columns = GridCells.Fixed(gridCells),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
            ) {
                if (prizeItems != null) {
                    items(prizeItems) {
                        PrizeItemCell(it)
                    }
                } else {
                    item {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }

}

@Composable
private fun PrizeItemCell(
    prizeItem: PrizeItem,
) {
    Box(
        Modifier
            .clip(MaterialTheme.shapes.medium)
            .aspectRatio(1f)
            .fillMaxWidth()
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color(0xFF181818),
        ) {

            // prizeItem.image
            Box {
                if (prizeItem.image === null) {
                    Image(
                        painterResource(R.drawable.prize_item_placeholder),
                        contentDescription = prizeItem.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    AsyncImage(
                        model = prizeItem.image,
                        contentDescription = prizeItem.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
                Box(Modifier.background(Color.White.copy(alpha = 0.5f)).fillMaxSize())
            }

            Column(Modifier.padding(4.dp).fillMaxSize()) {
                // prizeItem.name
                Text(
                    text = prizeItem.name,
                    fontSize = 16.sp,
                )
                // prizeItem.detail
                Text(
                    text = prizeItem.detail,
                    fontSize = 10.sp,
                    modifier = Modifier.weight(1f),
                )
                // prizeItem.stock,purchase
                if (prizeItem.stock >= 1) {
                    Text(
                        text = "${prizeItem.stock} / ${prizeItem.purchase}",
                        modifier = Modifier.align(Alignment.End),
                    )
                } else {
                    Text(
                        text = "売り切れ",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.End),
                    )
                }
            }

        }
    }
}
