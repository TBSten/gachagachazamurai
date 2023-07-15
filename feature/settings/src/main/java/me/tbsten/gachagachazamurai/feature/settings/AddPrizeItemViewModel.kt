package me.tbsten.gachagachazamurai.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.data.prizeItem.PrizeItemRepository
import me.tbsten.gachagachazamurai.domain.PrizeItem
import javax.inject.Inject

private val initialPrizeItem = PrizeItem(
    id = 0,
    name = "",
    detail = "",
    stock = 3,
    purchase = 3,
    image = null,
    rarity = PrizeItem.Rarity.NORMAL,
)

@HiltViewModel
class AddPrizeItemViewModel @Inject constructor(
    private val prizeItemRepository: PrizeItemRepository,
) : ViewModel() {
    private val _editingPrizeItem = MutableStateFlow(initialPrizeItem)
    val editingPrizeItem = _editingPrizeItem.asStateFlow()

    private val _addUiState = MutableStateFlow<AddPrizeItemUiState>(AddPrizeItemUiState.BeforeStart)
    val addUiState = _addUiState.asStateFlow()

    fun updatePrizeItem(newPrizeItem: PrizeItem) {
        _editingPrizeItem.update { newPrizeItem }
    }

    fun addPrizeItem() {
        _addUiState.update { AddPrizeItemUiState.Loading }
        viewModelScope.launch {
            try {
                prizeItemRepository.insertPrizeItem(editingPrizeItem.value)
                _editingPrizeItem.update { initialPrizeItem }
                _addUiState.update { AddPrizeItemUiState.Success }
                delay(5_000)
                _addUiState.update { AddPrizeItemUiState.BeforeStart }
            } catch (error: Exception) {
                _addUiState.update { AddPrizeItemUiState.Error(error.message) }
            }
        }
    }
}

sealed interface AddPrizeItemUiState {
    object BeforeStart : AddPrizeItemUiState
    object Loading : AddPrizeItemUiState
    object Success : AddPrizeItemUiState
    data class Error(val message: String?) : AddPrizeItemUiState
}
