package me.tbsten.gachagachazamurai.thanks

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import me.tbsten.gachagachazamurai.domain.Thanks
import me.tbsten.gachagachazamurai.file.thanks.thanksImageDir

@Composable
fun ThanksScreenContent(
    thanksViewModel: ThanksViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val thanks = thanksViewModel.thanks.collectAsState().value

    Scaffold(
        topBar = { ThanksTopBar() }
    ) { paddingValues ->
        if (thanks == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(Modifier.padding(paddingValues)) {
                items(thanks) { thanks ->
                    ThanksItem(
                        thanks = thanks,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(thanks.url)
                            })
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThanksTopBar() {
    TopAppBar(title = { Text("Thanks") })
}

@Composable
private fun ThanksItem(
    thanks: Thanks,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(modifier
        .clickable { onClick() }
        .padding(horizontal = 16.dp, vertical = 48.dp)
    ) {
        val context = LocalContext.current
        AsyncImage(
            context.thanksImageDir.resolve("${thanks.id}"),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier.align(Alignment.CenterHorizontally).heightIn(min = 200.dp),
        )
        Text(
            thanks.name,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}
