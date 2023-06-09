package me.tbsten.gachagachazamurai.setting.thanksEdit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.database.thanks.ThanksRepository
import me.tbsten.gachagachazamurai.domain.Thanks
import me.tbsten.gachagachazamurai.file.thanks.ThanksImageRepository
import javax.inject.Inject

val DEFAULT_THANKS = Thanks(
    id = 0,
    name = "◯◯ さん",
    url = "",
)

@HiltViewModel
class ThanksEditViewModel @Inject constructor(
    private val thanksRepository: ThanksRepository,
    private val thanksImageRepository: ThanksImageRepository,
) : ViewModel() {
    private val _thanks = MutableStateFlow<List<Thanks>?>(null)
    val thanks = _thanks.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _thanks.update {
                thanksRepository.getThanks()
            }
        }
    }

    fun addThanks(thanks: Thanks = DEFAULT_THANKS) {
        withSave {
            _thanks.update { it?.plus(thanks) }
        }
    }

    fun updateThanks(thanks: Thanks = DEFAULT_THANKS) {
        withSave(refresh = false) {
            _thanks.update { list -> list?.map { if (it.id == thanks.id) thanks else it } }
        }
    }

    fun deleteThanks(thanks: Thanks) {
        withSave {
            _thanks.update { list -> list?.filter { it.id != thanks.id } }
        }
    }

    fun updateThanksImage(id: Int, uri: Uri) {
        thanksImageRepository.saveImages(id, uri)
    }

    private fun withSave(refresh: Boolean = true, block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            block()
            val thanks = this@ThanksEditViewModel.thanks.value
                ?: throw IllegalStateException("thanks is null")
            thanksRepository.saveThanks(thanks)
            if (refresh) this@ThanksEditViewModel.refresh()
        }
    }

}
