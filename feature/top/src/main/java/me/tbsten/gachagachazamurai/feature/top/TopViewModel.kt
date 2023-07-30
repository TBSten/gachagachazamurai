package me.tbsten.gachagachazamurai.feature.top

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.tbsten.gachagachazamurai.data.topImage.TopImageRepository
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val topImageRepository: TopImageRepository,
) : ViewModel() {
    private val _images = MutableStateFlow<List<Uri>?>(null)
    val images = _images.asStateFlow()

    init {
        refreshImages()
    }

    fun refreshImages() {
        _images.update { topImageRepository.getImages().shuffled() }
    }
}
