package me.tbsten.gachagachazamurai.database.prizeitem

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.tbsten.gachagachazamurai.domain.PrizeItem

@Entity("prize_item")
data class PrizeItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val detail: String,
    val stock: Int,
    val purchase: Int,
    val image: String?,
    val rarity: PrizeItem.Rarity,
)

fun PrizeItemEntity.toPrizeItem(): PrizeItem =
    PrizeItem(
        id = this.id,
        name = this.name,
        detail = this.detail,
        stock = this.stock,
        purchase = this.purchase,
        image = if (this.image != null) Uri.parse(this.image) else null,
        rarity = this.rarity,
    )

fun PrizeItem.toEntity(): PrizeItemEntity =
    PrizeItemEntity(
        id = this.id,
        name = this.name,
        detail = this.detail,
        stock = this.stock,
        purchase = this.purchase,
        image = this.image.toString(),
        rarity = this.rarity,
    )