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
        get() = context.thanksImageDir

    fun saveImages(id: Int, uri: Uri) {
        saveImages("$id", uri)
    }

    fun saveImages(name: String, uri: Uri) {
        val resolver = context.contentResolver
        if (!imagesDir.exists()) Files.createDirectory(imagesDir.toPath())
        val inputStream = resolver.openInputStream(uri)
        inputStream.use { input ->
            if (input == null) return
            Files.copy(
                input,
                imagesDir.resolve(name).toPath(),
                StandardCopyOption.REPLACE_EXISTING,
            )
        }

    }

}

val Context.thanksImageDir
    get() = filesDir.resolve("thanks")

fun Thanks.imageUri(context: Context) = context
    .filesDir.resolve("thanks").resolve("$id")
