package me.tbsten.gachagachazamurai.data.prizeItem

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PrizeItemDao {
    @Query("SELECT * FROM prize_item")
    suspend fun getAll(): List<PrizeItemEntity>

    @Insert
    suspend fun insert(entity: PrizeItemEntity)

    @Update
    suspend fun update(entity: PrizeItemEntity)

    @Delete
    suspend fun delete(entity: PrizeItemEntity)

}
