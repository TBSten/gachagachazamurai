package me.tbsten.gachagachazamurai.database.thanks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ThanksDao {
    @Query("SELECT * FROM thanks")
    suspend fun getAll(): List<ThanksEntity>
    
    @Insert
    suspend fun insert(vararg entity: ThanksEntity)

    @Update
    suspend fun update(vararg entity: ThanksEntity)

    @Delete
    suspend fun delete(vararg entity: ThanksEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg entity: ThanksEntity)

    @Query("DELETE FROM thanks")
    suspend fun deleteAll()
}
