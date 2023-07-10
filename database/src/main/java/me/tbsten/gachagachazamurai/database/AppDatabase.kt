package me.tbsten.gachagachazamurai.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.tbsten.gachagachazamurai.database.prizeitem.PrizeItemDao
import me.tbsten.gachagachazamurai.database.prizeitem.PrizeItemEntity

@Database(entities = [PrizeItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prizeItemDao(): PrizeItemDao
}
