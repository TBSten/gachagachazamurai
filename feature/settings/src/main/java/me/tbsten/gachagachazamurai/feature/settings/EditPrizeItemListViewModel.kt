package me.tbsten.gachagachazamurai.feature.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tbsten.gachagachazamurai.data.prizeItem.PrizeItemRepository
import me.tbsten.gachagachazamurai.domain.PrizeItem
import javax.inject.Inject

@HiltViewModel
class EditPrizeItemListViewModel @Inject constructor(
    private val prizeItemRepository: PrizeItemRepository,
) : ViewModel() {
    private val _prizeItems = MutableStateFlow<List<PrizeItem>?>(null)
    val prizeItems = _prizeItems.asStateFlow()
}