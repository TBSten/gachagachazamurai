package me.tbsten.gachagachazamurai.feature.gacha.prizeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.data.prizeItem.PrizeItemRepository
import me.tbsten.gachagachazamurai.domain.PrizeItem
import javax.inject.Inject

@HiltViewModel
class PrizeListViewModel @Inject constructor(
    private val prizeItemRepository: PrizeItemRepository,
) : ViewModel() {
    private val _prizeItems = MutableStateFlow<List<PrizeItem>?>(null)
    val prizeItems = _prizeItems.asStateFlow()

    init {
        refreshPrizeItems()
    }

    fun refreshPrizeItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _prizeItems.update { prizeItemRepository.getAllPrizeItems() }
        }
    }

    private fun Job.refreshPrizeItemsOnComplete(): Job {
        invokeOnCompletion { refreshPrizeItems() }
        return this
    }


}
