package me.tbsten.gachagachazamurai.prize

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
class PrizeListViewModel @Inject constructor(
    private val prizeRepository: PrizeItemRepository,
) : ViewModel() {
    private val _items = MutableStateFlow<List<PrizeItem>?>(null)
    val items = _items.asStateFlow()
    private val _isLoadingItems = MutableStateFlow<Boolean>(false)
    val isLoadingItems = _isLoadingItems.asStateFlow()

    init {
        refreshPrizeItems()
    }

    fun refreshPrizeItems() {
        _isLoadingItems.update { true }
        viewModelScope.launch(Dispatchers.IO) {
            _items.update { prizeRepository.getAll() }
        }.invokeOnCompletion {
            _isLoadingItems.update { false }
        }
    }

    fun updatePrizeItem(item: PrizeItem) {
        viewModelScope.launch(Dispatchers.IO) {
            prizeRepository.update(item)
            refreshPrizeItems()
        }
    }

    fun deletePrizeItem(item: PrizeItem) {
        viewModelScope.launch(Dispatchers.IO) {
            prizeRepository.delete(item)
            refreshPrizeItems()
        }
    }

}
