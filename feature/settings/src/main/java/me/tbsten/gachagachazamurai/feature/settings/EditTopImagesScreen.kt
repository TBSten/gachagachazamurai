package me.tbsten.gachagachazamurai.feature.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.tbsten.gachagachazamurai.ui.component.ScreenTitle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditTopImagesScreen(
    editViewModel: EditTopImagesViewModel = hiltViewModel(),
) {
    val images by editViewModel.images.collectAsState()
    val addImageSelector =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                editViewModel.addTopImage(uri)
            }
        }

    LazyColumn {
        item {
            ScreenTitle { Text("トップの\n背景画像") }
        }
        item {
            ImagesSection(
                images = images,
                onDelete = { editViewModel.deleteTopImage(it) },
            )
        }
        item {
            FlowRow(Modifier.padding(16.dp)) {
                Button(onClick = {
                    addImageSelector.launch("image/*")
                }) {
                    Text("画像を追加")
                }
            }
        }
    }
}

@Composable
private fun ImagesSection(
    images: List<Uri>,
    onDelete: (Uri) -> Unit,
) {
    Row(
        Modifier
            .padding(16.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        images.forEach { image ->
            var openMenu by remember { mutableStateOf(false) }

            Column {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { openMenu = !openMenu }
                        .height(300.dp),
                )
                AnimatedVisibility(
                    visible = openMenu,
                    enter = expandVertically(),
                    exit = shrinkVertically(),
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { onDelete(image) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError,
                            ),
                        ) {
                            Text("削除")
                        }
                    }
                }
            }
        }
    }
}