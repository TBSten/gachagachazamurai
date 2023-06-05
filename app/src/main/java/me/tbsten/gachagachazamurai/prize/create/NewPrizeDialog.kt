package me.tbsten.gachagachazamurai.prize.create

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.prize.edit.PrizeEdit

val DEFAULT_INPUT_PRIZE_ITEM = PrizeItem(
    id = 0,
    name = "",
    detail = "",
    stock = 3,
    purchase = 3,
    image = null,
    rarity = PrizeItem.Rarity.NORMAL,
)

@Composable
fun NewPrizeDialog(
    newPrizeViewModel: NewPrizeViewModel = hiltViewModel(),
    backPrevScreen: () -> Unit,
) {
    Surface(
        Modifier.fillMaxSize().verticalScroll(state = rememberScrollState()),
    ) {
        Box(Modifier.padding(16.dp)) {

            PrizeEdit(
                modifier = Modifier.fillMaxWidth(),
                default = DEFAULT_INPUT_PRIZE_ITEM,
                title = {
                    Text("景品の追加", fontWeight = FontWeight.Bold)
                },
                actions = {
                    Button(onClick = {
                        Log.d("add-prize", "$it")
                        newPrizeViewModel.createPrizeItem(it)
                        backPrevScreen()
                    }) {
                        Text("追加")
                    }
                },
            )

        }
    }
}
