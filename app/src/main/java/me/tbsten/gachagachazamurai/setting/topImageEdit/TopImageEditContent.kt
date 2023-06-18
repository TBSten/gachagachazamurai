package me.tbsten.gachagachazamurai.setting.topImageEdit

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.tbsten.gachagachazamurai.component.clickableNoRipple
import me.tbsten.gachagachazamurai.file.rememberLauncherForMultiSelectFile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopImageEditContent(
    backToSetting: () -> Unit,
    topImageEditViewModel: TopImageEditViewModel = hiltViewModel(),
) {
    val _images by topImageEditViewModel.images.collectAsState()
    val images = _images

    val selectFilesState = rememberLauncherForMultiSelectFile(onChange = {
        topImageEditViewModel.addImages(it)
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("トップ画面の画像") }
            )
        },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {

            Row(
                Modifier
                    .padding(16.dp)
                    .height(400.dp)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                images?.forEach { uri ->
                    ImagePreview(
                        uri = uri,
                        onDelete = {
                            topImageEditViewModel.deleteImages(uri)
                        },
                    )
                }
            }

            Row {
                Button(onClick = {
                    selectFilesState.select("image/*")
                }) {
                    Text("追加")
                }
                Button(onClick = {
                    topImageEditViewModel.saveImages()
                }) {
                    Text("保存")
                }

            }

        }
    }
}

@Composable
private fun ImagePreview(
    uri: Uri,
    onDelete: () -> Unit,
) {
    var showBackdrop by remember { mutableStateOf(false) }
    val backdropAlpha by animateFloatAsState(
        label = "backdrop animation",
        targetValue = if (showBackdrop) 0.5f else 0f,
    )
    Box(Modifier.fillMaxHeight()) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight().clickableNoRipple { showBackdrop = !showBackdrop },
        )

        if (backdropAlpha != 0f) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = backdropAlpha))
            ) {
                IconButton(
                    onClick = {
                        onDelete()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    modifier = Modifier.align(Alignment.TopEnd),
                ) {
                    Icon(Icons.Default.Cancel, contentDescription = null)
                }
            }
        }
    }
}
