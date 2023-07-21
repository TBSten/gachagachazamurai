package me.tbsten.gachagachazamurai.feature.settings

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.ui.component.ListItem
import me.tbsten.gachagachazamurai.ui.component.ScreenTitle

@Composable
fun EditTopImagesScreen(
    editViewModel: EditTopImagesViewModel = hiltViewModel(),
) {
    val images by editViewModel.images.collectAsState()

    Column {
        ScreenTitle { Text("トップの\n背景画像") }
        ImagesSection(
            images = images,
        )
    }
}

@Composable
private fun ImagesSection(
    images: List<Uri>,
) {
    LazyRow(
        contentPadding = PaddingValues(16.dp),
    ) {
        items(images) { image ->
            ListItem {
                Text("$image")
            }
        }
    }
}