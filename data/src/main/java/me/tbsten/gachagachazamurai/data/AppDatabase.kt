package me.tbsten.gachagachazamurai.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.tbsten.gachagachazamurai.data.prizeItem.PrizeItemDao
import me.tbsten.gachagachazamurai.data.prizeItem.PrizeItemEntity

@Database(entities = [PrizeItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prizeItemDao(): PrizeItemDao
}
