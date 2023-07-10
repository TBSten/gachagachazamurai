package me.tbsten.gachagachazamurai.database.thanks

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.tbsten.gachagachazamurai.domain.Thanks

@Entity("thanks")
data class ThanksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val url: String,
)

fun ThanksEntity.toThanks(): Thanks =
    Thanks(
        id = id,
        name = name,
        url = url,
    )

fun Thanks.toEntity(): ThanksEntity =
    ThanksEntity(
        id = id,
        name = name,
        url = url,
    )