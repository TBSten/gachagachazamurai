package me.tbsten.gachagachazamurai.feature.gacha.gacha

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
}
