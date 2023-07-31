package me.tbsten.gachagachazamurai.data.prizeItem

import androidx.room.withTransaction
import me.tbsten.gachagachazamurai.database.AppDatabase
import me.tbsten.gachagachazamurai.database.prizeitem.toEntity
import me.tbsten.gachagachazamurai.database.prizeitem.toPrizeItem
import me.tbsten.gachagachazamurai.domain.PrizeItem
import me.tbsten.gachagachazamurai.file.AppFileSource
import javax.inject.Inject

class PrizeItemRepository @Inject constructor(
    private val db: AppDatabase,
    private val fileSource: AppFileSource,
) {
    private val prizeItemDao = db.prizeItemDao()

    suspend fun getRandomPrizeItem() =
        prizeItemDao.getRandom().toPrizeItem()

    suspend fun payoutPrizeItem(prizeItemId: Int) {
        db.withTransaction {
            val prizeItem = prizeItemDao.getOne(prizeItemId)
            if (prizeItem.stock <= 0) {
                throw IllegalStateException("can not use prizeItem . prize item stock is zero . prizeItem=$prizeItem")
            }
            val newPrizeItem = prizeItem.copy(stock = prizeItem.stock - 1)
            prizeItemDao.update(newPrizeItem)
        }
    }

    suspend fun getAllPrizeItems() =
        prizeItemDao.getAll().map { it.toPrizeItem() }

    suspend fun insertPrizeItem(prizeItem: PrizeItem) {
        var saveTargetPrizeItem = prizeItem
        val imageUri = prizeItem.image
        if (imageUri?.scheme == "content") {
            // 画像を保存
            val fileName = imageUri.hashCode().toString(36)
            val savedImageUri = fileSource.save(imageUri, fileName)
            saveTargetPrizeItem = prizeItem.copy(image = savedImageUri)
        }
        prizeItemDao.insert(saveTargetPrizeItem.toEntity())
    }

    suspend fun updatePrizeItem(prizeItem: PrizeItem) {
        var saveTargetPrizeItem = prizeItem
        val imageUri = prizeItem.image
        if (imageUri?.scheme == "content") {
            // 画像を保存
            val fileName = imageUri.hashCode().toString(36)
            val savedImageUri = fileSource.save(imageUri, fileName)
            saveTargetPrizeItem = prizeItem.copy(image = savedImageUri)
        }
        prizeItemDao.update(prizeItem.toEntity())
    }

    suspend fun deletePrizeItem(prizeItem: PrizeItem) =
        prizeItemDao.delete(prizeItem.toEntity())

    suspend fun deletePrizeItem(prizeItemId: Int) =
        prizeItemDao.delete(prizeItemId)
}
