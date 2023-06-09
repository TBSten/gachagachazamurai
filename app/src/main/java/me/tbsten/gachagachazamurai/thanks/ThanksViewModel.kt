package me.tbsten.gachagachazamurai.thanks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.database.thanks.ThanksRepository
import me.tbsten.gachagachazamurai.domain.Thanks
import javax.inject.Inject

@HiltViewModel
class ThanksViewModel @Inject constructor(
    private val thanksRepository: ThanksRepository,
) : ViewModel() {
    private val _thanks = MutableStateFlow<List<Thanks>?>(null)
    val thanks = _thanks.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _thanks.update { thanksRepository.getThanks() }
        }
    }
}