package me.tbsten.gachagachazamurai.feature.settings.thanks

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.data.thanks.ThanksRepository
import me.tbsten.gachagachazamurai.domain.Thanks
import javax.inject.Inject

@HiltViewModel
class EditThanksViewModel @Inject constructor(
    private val thanksRepository: ThanksRepository,
) : ViewModel() {
    private val _thanksList = MutableStateFlow(listOf<Thanks>())
    val thanksList = _thanksList.asStateFlow()

    init {
        refreshThanksList()
    }

    fun refreshThanksList() {
        viewModelScope.launch(Dispatchers.IO) {
            _thanksList.update { thanksRepository.getThanks() }
        }
    }

    fun addNewThanks() {
        viewModelScope.launch(Dispatchers.IO) {
            // add to _thanksList
            val newThanks = Thanks(
                id = 0,
                name = "つーばーさ",
                url = Uri.parse("https://twitter.com/tsuba__zutomaro?s=20"),
                image = Uri.parse("https://pbs.twimg.com/profile_images/1667161765052928002/AqgY8eIq_400x400.jpg"),
            )
            _thanksList.update {
                it.toMutableList().apply {
                    add(newThanks)
                }
            }

            thanksRepository.saveThanks(thanksList.value)
        }.invokeOnCompletion { refreshThanksList() }
    }

    fun saveThanks(thanks: Thanks) {
        viewModelScope.launch(Dispatchers.IO) {
            thanksRepository.saveThanks(thanks)
        }.invokeOnCompletion { refreshThanksList() }
    }

    fun deleteThanks(thanks: Thanks) {
        viewModelScope.launch(Dispatchers.IO) {
            thanksRepository.deleteThanks(thanks)
        }.invokeOnCompletion { refreshThanksList() }
    }
}