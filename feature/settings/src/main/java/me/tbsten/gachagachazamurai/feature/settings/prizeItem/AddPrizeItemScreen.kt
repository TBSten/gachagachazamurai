package me.tbsten.gachagachazamurai.feature.settings.prizeItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.tbsten.gachagachazamurai.ui.component.ImageSelect

@Composable
fun AddPrizeItemScreen(
    addPrizeItemViewModel: AddPrizeItemViewModel = hiltViewModel(),
) {
    val editingPrizeItem by addPrizeItemViewModel.editingPrizeItem.collectAsState()
    val addUiState = addPrizeItemViewModel.addUiState.collectAsState().value

    Column(Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = editingPrizeItem.name,
            onValueChange = { addPrizeItemViewModel.updatePrizeItem(editingPrizeItem.copy(name = it)) },
            label = { Text("景品名") },
            singleLine = true,
        )
        OutlinedTextField(
            value = editingPrizeItem.detail,
            onValueChange = { addPrizeItemViewModel.updatePrizeItem(editingPrizeItem.copy(detail = it)) },
            label = { Text("景品名") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
        )
        ImageSelect(
            file = editingPrizeItem.image,
            onFileChange = { addPrizeItemViewModel.updatePrizeItem(editingPrizeItem.copy(image = it)) },
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { addPrizeItemViewModel.addPrizeItem() },
            modifier = Modifier.align(Alignment.End),
            enabled = addUiState !is AddPrizeItemUiState.Loading,
        ) {
            if ((addUiState is AddPrizeItemUiState.Loading)) {
                CircularProgressIndicator(color = LocalContentColor.current)
            }
            Text("追加")
        }
        when (addUiState) {
            AddPrizeItemUiState.Success, is AddPrizeItemUiState.Error -> {
                Box(Modifier.padding(8.dp)) {
                    Message(addUiState.toMessageType()) {
                        when (addUiState) {
                            AddPrizeItemUiState.Success -> Text("追加しました")
                            is AddPrizeItemUiState.Error -> Text("エラーが発生しました :\n${addUiState.message}")
                            else -> {}
                        }
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun Message(
    type: MessageType,
    content: @Composable () -> Unit,
) {
    val bgColor =
        when (type) {
            MessageType.SUCCESS -> Color(0xFFAFE1AF)
            MessageType.ERROR -> MaterialTheme.colorScheme.error
        }
    val textColor =
        when (type) {
            MessageType.SUCCESS -> Color.White
            MessageType.ERROR -> MaterialTheme.colorScheme.onError
        }

    Row(
        Modifier
            .background(bgColor)
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides textColor
        ) {
            content()
        }
    }
}

private enum class MessageType {
    ERROR,
    SUCCESS,
    ;
}

private fun AddPrizeItemUiState.toMessageType(): MessageType =
    when (this) {
        AddPrizeItemUiState.Success -> MessageType.SUCCESS
        is AddPrizeItemUiState.Error -> MessageType.ERROR
        else -> throw Exception("NOT IMPLEMENT, can not convert $this to MessageType")
    }
