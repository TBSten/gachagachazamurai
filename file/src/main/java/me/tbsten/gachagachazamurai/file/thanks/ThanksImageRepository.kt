package me.tbsten.gachagachazamurai.file.thanks

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import me.tbsten.gachagachazamurai.domain.Thanks
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import javax.inject.Inject

class ThanksImageRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val imagesDir: File
        get() = context.filesDir.resolve("thanks")

    fun saveImages(map: Map<Int, Uri>) {
        val resolver = context.contentResolver
        map.forEach { (id, uri) ->
            val inputStream = resolver.openInputStream(uri)
            inputStream.use { input ->
                if (input == null) return
                val destPath = "$id"
                Files.copy(
                    input,
                    imagesDir.resolve(destPath).toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                )
            }
        }
    }

    fun getImageUri(id: Int) =
        Uri.fromFile(imagesDir.resolve("$id"))
}

fun Thanks.imageUri(context: Context) = context
    .filesDir.resolve("thanks").resolve("$id")
