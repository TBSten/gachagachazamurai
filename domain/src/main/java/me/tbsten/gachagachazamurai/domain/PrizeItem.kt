package me.tbsten.gachagachazamurai.domain

data class PrizeItem(
    val id: Int,
    val name: String,
    val detail: String,
    val stock: Int,
    val purchase: Int,
    val image: String? = null,
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
