package me.tbsten.gachagachazamurai.data.prizeItem

import androidx.room.withTransaction
import me.tbsten.gachagachazamurai.database.AppDatabase
import me.tbsten.gachagachazamurai.database.prizeitem.toEntity
import me.tbsten.gachagachazamurai.database.prizeitem.toPrizeItem
import me.tbsten.gachagachazamurai.domain.PrizeItem
import javax.inject.Inject

class PrizeItemRepository @Inject constructor(
    private val db: AppDatabase,
) {
    private val prizeItemDao = db.prizeItemDao()

    private suspend fun getRandomPrizeItem() =
        prizeItemDao.getRandom().toPrizeItem()

    private suspend fun payoutPrizeItem(prizeItemId: Int) {
        db.withTransaction {
            val prizeItem = prizeItemDao.getOne(prizeItemId)
            if (prizeItem.stock <= 0) {
                throw IllegalStateException("can not use prizeItem . prize item stock is zero . prizeItem=$prizeItem")
            }
            val newPrizeItem = prizeItem.copy(stock = prizeItem.stock - 1)
            prizeItemDao.update(newPrizeItem)
        }
    }

    private suspend fun getAllPrizeItems() =
        prizeItemDao.getAll().map { it.toPrizeItem() }

    private suspend fun insertPrizeItem(prizeItem: PrizeItem) =
        prizeItemDao.insert(prizeItem.toEntity())

    private suspend fun updatePrizeItem(prizeItem: PrizeItem) =
        prizeItemDao.update(prizeItem.toEntity())

    private suspend fun deletePrizeItem(prizeItem: PrizeItem) =
        prizeItemDao.delete(prizeItem.toEntity())

    private suspend fun deletePrizeItem(prizeItemId: Int) =
        prizeItemDao.delete(prizeItemId)
}
