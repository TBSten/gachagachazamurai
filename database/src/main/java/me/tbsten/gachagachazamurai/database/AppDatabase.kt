package me.tbsten.gachagachazamurai.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.tbsten.gachagachazamurai.database.prizeItem.PrizeItemDao
import me.tbsten.gachagachazamurai.database.prizeItem.PrizeItemEntity
import me.tbsten.gachagachazamurai.database.thanks.ThanksDao
import me.tbsten.gachagachazamurai.database.thanks.ThanksEntity

@Database(
    entities = [PrizeItemEntity::class, ThanksEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prizeItemDao(): PrizeItemDao
    abstract fun thanksDao(): ThanksDao
}
