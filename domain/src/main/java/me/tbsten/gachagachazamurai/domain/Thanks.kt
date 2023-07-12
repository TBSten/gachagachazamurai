package me.tbsten.gachagachazamurai.domain

import android.net.Uri

data class Thanks(
    val id: Int,
    val name: String,
    val url: Uri,
    val image: Uri,
)
