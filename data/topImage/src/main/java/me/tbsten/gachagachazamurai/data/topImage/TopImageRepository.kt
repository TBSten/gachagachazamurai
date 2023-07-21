package me.tbsten.gachagachazamurai.data.topImage

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import me.tbsten.gachagachazamurai.file.AppFileSource
import javax.inject.Inject

private const val TOP_IMAGE_FILE_DIR = "top_image"

class TopImageRepository @Inject constructor(
    private val appFileSource: AppFileSource,
) {
    fun getImages(): List<Uri> =
        appFileSource
            .getFiles(TOP_IMAGE_FILE_DIR)
            ?.map { it.toUri() }
            ?: listOf()

    fun saveImage(image: Uri): Uri {
        val fileName = image.toSaveFileName()
        return appFileSource.save(image, "$TOP_IMAGE_FILE_DIR/$fileName")
    }

    fun deleteImage(image: Uri) {
        val fileName = image.toSaveFileName()
        Log.d("delete-image", "$image >> $TOP_IMAGE_FILE_DIR/$fileName")
        appFileSource.deleteFile("$TOP_IMAGE_FILE_DIR/$fileName")
    }

    private fun Uri.toSaveFileName() =
        if (this.scheme == "content")
            this.hashCode().toString(36)
        else
            this.pathSegments.last()
}
