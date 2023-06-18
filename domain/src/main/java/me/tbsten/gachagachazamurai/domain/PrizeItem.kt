package me.tbsten.gachagachazamurai.domain

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
        val displayName: String,
    ) {
        NORMAL("ノーマル"),
        RARE("レア"),
        SUPER_RARE("スーパーレア");
    }

}
