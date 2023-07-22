package me.tbsten.gachagachazamurai.file

import android.net.Uri

fun parseUriOrNull(uriString: String): Uri? {
    return try {
        Uri.parse(uriString)
    } catch (e: Exception) {
        null
    }
}
