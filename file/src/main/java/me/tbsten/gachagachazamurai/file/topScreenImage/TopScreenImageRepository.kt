package me.tbsten.gachagachazamurai.file.topScreenImage

import android.content.Context
import android.net.Uri
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.BasicFileAttributes
import javax.inject.Inject
import kotlin.io.path.createDirectory

class TopScreenImageRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val imagesDir: File
        get() = context.topScreenImagesDir

    fun getTopScreenImages(): List<File>? {
        if (!imagesDir.exists()) return listOf()
        return imagesDir.listFiles()?.toList()
    }

    fun saveTopScreenImages(uris: List<Uri>) {
        if (!imagesDir.exists()) imagesDir.toPath().createDirectory()

        // ファイルの追加・更新
        val resolver = context.contentResolver
        uris.forEachIndexed { idx, uri ->
            Log.d("put", "idx:$idx uri:$uri")
            val inputStream = resolver.openInputStream(uri)
            inputStream.use { input ->
                if (input == null) return
                val destPath = "$idx"
                Files.copy(
                    input,
                    imagesDir.resolve(destPath).toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                )
            }
        }
        // ファイルの削除
        imagesDir.listFiles()
            ?.filterIndexed { index, file -> uris.size <= index }
            ?.forEach {
                Log.d("delete", "file:$it")
                Files.delete(it.toPath())
            }
    }

}

val Context.topScreenImagesDir
    get() = filesDir.resolve("topScreenImages")

fun Path.deleteRecursive() {
    if (!Files.exists(this)) return
    Files.walkFileTree(this, object : SimpleFileVisitor<Path>() {
        override fun visitFile(path: Path?, attr: BasicFileAttributes?): FileVisitResult {
            Log.d("delete-r:delete-file", "$path")
            Files.delete(path)
            return FileVisitResult.CONTINUE
        }

        override fun postVisitDirectory(path: Path?, ex: IOException?): FileVisitResult {
            Log.d("delete-r:delete-dir", "$path")
            Files.delete(path)
            return FileVisitResult.CONTINUE
        }
    })
}