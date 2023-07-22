package me.tbsten.gachagachazamurai.feature.settings.thanks

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.tbsten.gachagachazamurai.domain.Thanks
import me.tbsten.gachagachazamurai.ui.component.BottomSheet
import me.tbsten.gachagachazamurai.ui.component.BottomSheetState
import me.tbsten.gachagachazamurai.ui.component.ImageSelect
import me.tbsten.gachagachazamurai.ui.component.ScreenTitle
import me.tbsten.gachagachazamurai.ui.component.rememberBottomSheetState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditThanksScreen(
    thanksViewModel: EditThanksViewModel = hiltViewModel(),
) {
    val thanksList by thanksViewModel.thanksList.collectAsState()

    LazyColumn {
        item {
            ScreenTitle { Text("Thanks") }
        }
        items(thanksList, key = { it.id }) { thanks ->
            ThanksItem(
                modifier = Modifier.animateItemPlacement(),
                thanks = thanks,
                onSave = { thanksViewModel.saveThanks(it) },
                onDelete = { thanksViewModel.deleteThanks(thanks) },
            )
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { thanksViewModel.addNewThanks() }) {
                    Text("追加")
                }
            }
        }
    }
}

@Composable
private fun ThanksItem(
    thanks: Thanks,
    modifier: Modifier = Modifier,
    onSave: (Thanks) -> Unit,
    onDelete: () -> Unit,
) {
    val bottomSheetState = rememberBottomSheetState()

    Column(
        modifier
            .clickable { bottomSheetState.show() }
            .fillMaxWidth()
            .padding(vertical = 48.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier.aspectRatio(1f).fillMaxWidth(),
            model = thanks.image,
            contentDescription = thanks.name,
            contentScale = ContentScale.Crop,
        )
        Text(
            text = thanks.name,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
    ThanksEditSheet(
        bottomSheetState,
        thanks,
        onSave = {
            onSave(it)
            bottomSheetState.hide()
        },
        onDelete = onDelete,
    )
}

@Composable
private fun ThanksEditSheet(
    bottomSheetState: BottomSheetState,
    thanks: Thanks,
    onSave: (Thanks) -> Unit,
    onDelete: () -> Unit,
) {
    BottomSheet(
        bottomSheetState,
    ) {

        var name by remember(thanks.name) { mutableStateOf(thanks.name) }
        var url by remember(thanks.url) { mutableStateOf(thanks.url.toString()) }
        var image by remember(thanks.image) { mutableStateOf(thanks.image) }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("名前") },
            )

            TextField(
                value = url,
                onValueChange = { url = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("リンク") },
            )

            ImageSelect(
                file = image,
                onFileChange = { if (it != null) image = it },
            )

            Spacer(Modifier.size(48.dp))

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    onSave(
                        thanks.copy(
                            name = name,
                            url = Uri.parse(url),
                            image = image,
                        )
                    )
                }) {
                    Text("保存")
                }
            }

            Divider(Modifier.padding(vertical = 16.dp))

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                ) {
                    Text("削除")
                }
            }

        }
    }

}

@Composable
private fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = {},
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = LocalTextStyle.current,
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    CompositionLocalProvider(
                        LocalContentColor provides LocalContentColor.current.let { it.copy(alpha = it.alpha * 0.7f) },
                    ) {
                        placeholder()
                    }
                }
                innerTextField()
            }
        }
    )
}
