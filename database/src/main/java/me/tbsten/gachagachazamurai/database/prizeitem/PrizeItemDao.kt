package me.tbsten.gachagachazamurai.database.prizeitem

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlin.math.floor

@Dao
interface PrizeItemDao {
    @Query("SELECT * FROM prize_item")
    suspend fun getAll(): List<PrizeItemEntity>

    @Query("SELECT * FROM prize_item ORDER BY id ASC")
    suspend fun getAllSortById(): List<PrizeItemEntity>

    @Query("SELECT * FROM prize_item ORDER BY name, id ASC")
    suspend fun getAllSortByName(): List<PrizeItemEntity>

    @Query("SELECT * FROM prize_item ORDER BY stock DESC, id ASC")
    suspend fun getAllSortByStock(): List<PrizeItemEntity>

    @Query("SELECT * FROM prize_item ORDER BY purchase DESC, id ASC")
    suspend fun getAllSortByPurchase(): List<PrizeItemEntity>

    @Query("SELECT * FROM prize_item ORDER BY rarity DESC, id ASC")
    suspend fun getAllSortByRarity(): List<PrizeItemEntity>

    suspend fun getAll(sortBy: String): List<PrizeItemEntity> =
        when (sortBy) {
            "id" -> getAllSortById()
            "name" -> getAllSortByName()
            "stock" -> getAllSortByStock()
            "purchase" -> getAllSortByPurchase()
            "rarity" -> getAllSortByRarity()
            else -> throw IllegalArgumentException("$sortBy is invalid as sortBy")
        }


    @Query("SELECT * FROM prize_item WHERE id = :id")
    suspend fun getOne(id: Int): PrizeItemEntity

    @Query("SELECT COUNT(*) FROM prize_item")
    suspend fun getAllCount(): Int

    @Insert
    suspend fun insert(entity: PrizeItemEntity)

    @Update
    suspend fun update(entity: PrizeItemEntity)

    @Delete
    suspend fun delete(entity: PrizeItemEntity)

    @Query("DELETE FROM prize_item WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT SUM(stock) FROM prize_item")
    fun _getTotalStock(): Int

    @Query(
        "SELECT a.stock FROM prize_item as a INNER JOIN prize_item as b ON a.stock >= b.stock " +
                "WHERE a.stock >= 1 " +
                "GROUP BY a.id " +
                "HAVING SUM(b.stock) > :randomStock " +
                "ORDER BY a.stock ASC " +
                "LIMIT 1 "
    )
    fun _getRandomStock(
        randomStock: Double = floor(Math.random() * _getTotalStock()),
    ): Int

    @Query("SELECT * FROM prize_item WHERE stock = :stock order by RANDOM() LIMIT 1")
    suspend fun getRandom(stock: Int = _getRandomStock()): PrizeItemEntity
}
