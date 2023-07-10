package me.tbsten.gachagachazamurai.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.tbsten.gachagachazamurai.database.prizeitem.PrizeItemDao
import me.tbsten.gachagachazamurai.database.prizeitem.PrizeItemEntity
import me.tbsten.gachagachazamurai.database.thanks.ThanksDao
import me.tbsten.gachagachazamurai.database.thanks.ThanksEntity

@Database(entities = [PrizeItemEntity::class, ThanksEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prizeItemDao(): PrizeItemDao
    abstract fun thanksDao(): ThanksDao
}
