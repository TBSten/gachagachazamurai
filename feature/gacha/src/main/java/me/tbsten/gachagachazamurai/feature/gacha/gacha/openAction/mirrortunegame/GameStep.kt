package me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.mirrortunegame

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import me.tbsten.gachagachazamurai.feature.gacha.R

enum class GameStep {
    PREPARE,
    PLAYING,
    FINISHED,
    COMPLETED,
}

@Composable
internal fun rememberGameStepState(): GameStepState {
    return remember { GameStepState() }
}

internal class GameStepState {
    var step by mutableStateOf(GameStep.PREPARE)

    fun onStartPlay() {
        this.step = GameStep.PLAYING
    }

    fun onFinishPlay(point: Int): GameResult {
        this.step = GameStep.FINISHED
        return GameResult.pointToGameResult(point)
    }

    fun onCompleted() {
        this.step = GameStep.COMPLETED
    }
}

enum class GameResult(
    @DrawableRes val image: Int,
    val text: String,
) {
    GOOD(R.drawable.good_1, "good"),
    VERY_GOOD(R.drawable.very_good_2, "very good"),
    BOW(R.drawable.bow_1, "bow"),
    ;

    companion object {
        fun pointToGameResult(point: Int) = when {
            70 <= point -> BOW
            50 <= point -> VERY_GOOD
            else -> GOOD
        }
    }

}
