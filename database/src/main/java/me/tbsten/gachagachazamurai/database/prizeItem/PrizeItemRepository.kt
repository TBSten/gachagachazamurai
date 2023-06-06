package me.tbsten.gachagachazamurai.database.prizeItem

import me.tbsten.gachagachazamurai.database.AppDatabase
import me.tbsten.gachagachazamurai.domain.PrizeItem
import javax.inject.Inject

class PrizeItemRepository @Inject constructor(
    db: AppDatabase,
) {
    private val prizeItemDao = db.prizeItemDao()
    suspend fun create(item: PrizeItem) {
        prizeItemDao.insert(item.toEntity())
    }

    suspend fun getAll(): List<PrizeItem> {
        return prizeItemDao.getAll().map { it.toPrizeItem() }
    }

    suspend fun update(item: PrizeItem) {
        prizeItemDao.update(item.toEntity())
    }

    suspend fun delete(item: PrizeItem) {
        prizeItemDao.delete(item.toEntity())
    }
}
