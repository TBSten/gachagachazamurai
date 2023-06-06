package me.tbsten.gachagachazamurai.prize.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.database.prizeItem.PrizeItemRepository
import me.tbsten.gachagachazamurai.domain.PrizeItem
import javax.inject.Inject

@HiltViewModel
class NewPrizeViewModel @Inject constructor(
    private val prizeRepository: PrizeItemRepository,
) : ViewModel() {
    fun createPrizeItem(item: PrizeItem) {
        viewModelScope.launch(Dispatchers.IO) {
            prizeRepository.create(item)
        }
    }
}
