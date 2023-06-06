package me.tbsten.gachagachazamurai.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.tbsten.gachagachazamurai.database.prizeItem.PrizeItemDao
import me.tbsten.gachagachazamurai.database.prizeItem.PrizeItemEntity

@Database(entities = [PrizeItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prizeItemDao(): PrizeItemDao
}
