package me.tbsten.gachagachazamurai.gacha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.database.prizeItem.PrizeItemRepository
import me.tbsten.gachagachazamurai.domain.PrizeItem
import javax.inject.Inject

@HiltViewModel
class GachaViewModel @Inject constructor(
    private val prizeItemRepository: PrizeItemRepository,
) : ViewModel() {
    private val _targetPrizeItem = MutableStateFlow<PrizeItem?>(null)
    val targetPrizeItem = _targetPrizeItem.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _targetPrizeItem.update { prizeItemRepository.getRandom() }
        }
    }

}
