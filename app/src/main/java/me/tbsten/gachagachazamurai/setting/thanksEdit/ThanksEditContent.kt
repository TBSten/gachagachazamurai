package me.tbsten.gachagachazamurai.setting.thanksEdit

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import me.tbsten.gachagachazamurai.R
import me.tbsten.gachagachazamurai.component.errorButtonColors
import me.tbsten.gachagachazamurai.component.primaryButtonColors
import me.tbsten.gachagachazamurai.domain.Thanks
import me.tbsten.gachagachazamurai.file.rememberLauncherForSelectFile
import me.tbsten.gachagachazamurai.file.thanks.thanksImageDir

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThanksEditContent(
    backToSetting: () -> Unit,
    thanksEditViewModel: ThanksEditViewModel = hiltViewModel(),
) {
    val thanks = thanksEditViewModel.thanks.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thanksの編集") },
                actions = {
                    IconButton(onClick = { thanksEditViewModel.refresh() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {

            LazyColumn {
                if (thanks != null) {
                    items(thanks) { thanks ->
                        ThanksEditListItem(
                            thanks = thanks,
                            onChange = { thanksEditViewModel.updateThanks(it) },
                            onDelete = { thanksEditViewModel.deleteThanks(thanks) },
                            onChangeImage = {
                                thanksEditViewModel.updateThanksImage(
                                    thanks.id,
                                    it
                                )
                            },
                        )
                    }
                }
                item {
                    Box(Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { thanksEditViewModel.addThanks() },
                            modifier = Modifier.align(Alignment.Center),
                        ) {
                            Text("追加")
                        }
                    }
                }

            }

        }
    }

}

@Composable
fun ThanksEditListItem(
    thanks: Thanks,
    onChange: (Thanks) -> Unit,
    onDelete: () -> Unit,
    onChangeImage: (Uri) -> Unit,
) {
    val context = LocalContext.current
    var editing by remember(thanks) { mutableStateOf(thanks) }
    val imageUri = remember { Uri.fromFile(context.thanksImageDir.resolve("${thanks.id}")) }
    var openDialog by remember { mutableStateOf(false) }

    val imageModel = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(imageUri)
            .placeholder(R.drawable.loading)
            .build()
    )

    val imageSelector =
        rememberLauncherForSelectFile(onChange = {
            Log.d("selectImage", "$it")
            if (it == null) return@rememberLauncherForSelectFile
            onChangeImage(it)
            imageModel.onForgotten()
            imageModel.onRemembered()
        })

    ListItem(
        leadingContent = {
            Image(
                imageModel, contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        },
        headlineContent = {
            Text(thanks.name)
        },
        modifier = Modifier.clickable {
            openDialog = true
        },
    )

    if (openDialog) {
        Dialog(onDismissRequest = { openDialog = false }) {
            Surface(shape = MaterialTheme.shapes.large) {

                Column(Modifier.padding(16.dp).verticalScroll(state = rememberScrollState())) {
                    TextField(
                        label = { Text("名前") },
                        value = editing.name,
                        onValueChange = { editing = editing.copy(name = it) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    TextField(
                        label = { Text("URL") },
                        value = editing.url,
                        onValueChange = { editing = editing.copy(url = it) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Image(
                        painter = imageModel,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                            .clickable { imageSelector.select("image/*") },
                    )

                    Row(
                        modifier = Modifier.padding(top = 32.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            onClick = {
                                openDialog = false
                                onDelete()
                            },
                            colors = errorButtonColors,
                        ) {
                            Text("削除")
                        }
                        Button(
                            onClick = {
                                onChange(editing)
                                openDialog = false
                            },
                            colors = primaryButtonColors,
                        ) {
                            Text("保存")
                        }
                    }

                }

            }
        }
    }

}
