package me.tbsten.gachagachazamurai.file

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.io.path.createDirectory

class AppFileSource @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun save(uri: Uri, dst: String): Uri {
        val parentDir = context.filesDir.resolve(dst).parentFile
            ?: throw Exception("not implement .")
        // 親ファイルが存在していなかったら作成
        if (!parentDir.exists()) {
            parentDir.toPath().createDirectory()
        }
        val resolver = context.contentResolver
        val file = resolver.openInputStream(uri).use {
            val file = context.filesDir.resolve(dst)
            val fileStream = file.outputStream()
            it?.copyTo(fileStream)
            file
        }
        return Uri.fromFile(file)
    }

    fun getFiles(dir: String) =
        context.filesDir.resolve(dir).listFiles()?.toList()

}
