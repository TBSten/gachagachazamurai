package me.tbsten.gachagachazamurai.feature.gacha.gacha

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.data.prizeItem.PrizeItemRepository
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.feature.gacha.NavigationTextState
import me.tbsten.gachagachazamurai.feature.gacha.R
import me.tbsten.gachagachazamurai.feature.gacha.gacha.openAction.OpenActionState
import me.tbsten.gachagachazamurai.feature.gacha.gacha.result.GachaResultState
import javax.inject.Inject

@HiltViewModel
class GachaPlayViewModel @Inject constructor(
    private val prizeItemRepository: PrizeItemRepository,
) : ViewModel() {
    private val _prizeItem = MutableStateFlow<PrizeItem?>(null)
    val prizeItem = _prizeItem.asStateFlow()

    init {
        refreshPrizeItem()
    }

    private fun refreshPrizeItem() {
        viewModelScope.launch(Dispatchers.IO) {
            _prizeItem.update { prizeItemRepository.getRandomPrizeItem() }
        }
    }

    val gachaState =
        GachaState(
            scale = 0.8f,
            enableRotate = false,
            handleRotate = 0f,
        )

    val openActionState =
        OpenActionState(
            open = false,
        )

    val gachaResultState =
        GachaResultState(
            open = false,
        )
    val navigationTextState =
        NavigationTextState()

    fun startGacha() {
        gachaState.enableRotate = true
        gachaState.scale = 1.0f

        navigationTextState.show(R.drawable.tap_1, "タップ !")
    }

    fun rotate() {
        navigationTextState.clear()
        gachaState.rotate(180f)
    }

    fun startOpenAction() {
        openActionState.show()
    }

    fun showResult() {
        gachaResultState.show()
    }

    fun showNavigationText(@DrawableRes drawable: Int, text: String) {
        navigationTextState.show(drawable, text)
    }

    fun clearNavigationText() {
        navigationTextState.clear()
    }
}
