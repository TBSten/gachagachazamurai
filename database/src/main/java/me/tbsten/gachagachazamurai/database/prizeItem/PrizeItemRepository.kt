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

    suspend fun getAll(sortBy: String = "id"): List<PrizeItem> {
        return prizeItemDao.getAll(sortBy = sortBy).map { it.toPrizeItem() }
    }

    suspend fun getAllCount(): Int {
        return prizeItemDao.getAllCount()
    }

    suspend fun getRandom(): PrizeItem {
        return prizeItemDao.getRandom().toPrizeItem()
    }

    suspend fun update(
        item: PrizeItem,
    ) {
        prizeItemDao.update(item.toEntity())
    }

    suspend fun delete(item: PrizeItem) {
        prizeItemDao.delete(item.id)
    }
}
