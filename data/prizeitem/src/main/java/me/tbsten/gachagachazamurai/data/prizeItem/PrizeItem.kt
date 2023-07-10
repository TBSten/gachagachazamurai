package me.tbsten.gachagachazamurai.data.prizeItem

import android.net.Uri

data class PrizeItem(
    val id: Int,
    val name: String,
    val detail: String,
    val stock: Int,
    val purchase: Int,
    val image: Uri? = null,
    val rarity: Rarity = Rarity.NORMAL,
) {
    enum class Rarity(
        val id: Int,
        val displayName: String,
    ) {
        NORMAL(1, "ノーマル"),
        RARE(2, "レア"),
        SUPER_RARE(3, "スーパーレア");
    }
}
