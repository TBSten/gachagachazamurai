package me.tbsten.gachagachazamurai.data

import androidx.room.TypeConverter
import me.tbsten.gachagachazamurai.domain.PrizeItem

class Converters {
    @TypeConverter
    fun rarityToName(rarity: PrizeItem.Rarity): String {
        return rarity.name
    }

    @TypeConverter
    fun nameToRarity(rarityName: String): PrizeItem.Rarity {
        return PrizeItem.Rarity.valueOf(rarityName)
    }

}