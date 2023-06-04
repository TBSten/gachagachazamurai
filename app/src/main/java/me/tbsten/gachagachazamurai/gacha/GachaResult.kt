package me.tbsten.gachagachazamurai.gacha

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.tbsten.gachagachazamurai.component.animation.FadeIn
import me.tbsten.gachagachazamurai.domain.PrizeItem

@Composable
fun GachaResult(
    prizeItem: PrizeItem,
    actions: (@Composable () -> Unit)? = null,
) {

    FadeIn {
        ElevatedCard(
            modifier = Modifier.padding(16.dp),
        ) {
            LazyColumn(modifier = Modifier.padding(24.dp)) {

                item {
                    with(prizeItem) {
                        Text(name)
                        Text(detail)
                        Text("$stock / $purchase")
                    }
                }

                item {
                    actions?.let { actions ->
                        FadeIn(delayMillis = 1000) {
                            Row(
                                modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                            ) {
                                actions()
                            }
                        }
                    }
                }

            }
        }
    }

}
