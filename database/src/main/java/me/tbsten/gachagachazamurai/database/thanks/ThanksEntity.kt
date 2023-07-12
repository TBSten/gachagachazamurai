package me.tbsten.gachagachazamurai.database.thanks

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.tbsten.gachagachazamurai.domain.Thanks

@Entity("thanks")
data class ThanksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val url: String,
    val image: String,
)

fun ThanksEntity.toThanks(): Thanks =
    Thanks(
        id = id,
        name = name,
        url = Uri.parse(url),
        image = Uri.parse(image),
    )

fun Thanks.toEntity(): ThanksEntity =
    ThanksEntity(
        id = id,
        name = name,
        url = url.toString(),
        image = image.toString(),
    )
