package me.tbsten.gachagachazamurai.data.thanks

import me.tbsten.gachagachazamurai.database.AppDatabase
import me.tbsten.gachagachazamurai.database.thanks.ThanksDao
import me.tbsten.gachagachazamurai.database.thanks.toEntity
import me.tbsten.gachagachazamurai.database.thanks.toThanks
import me.tbsten.gachagachazamurai.domain.Thanks
import javax.inject.Inject

class ThanksRepository @Inject constructor(
    private val db: AppDatabase,
) {
    private val thanksDao: ThanksDao
        get() = db.thanksDao()

    suspend fun getThanks() = thanksDao.getAll().map { it.toThanks() }
    suspend fun saveThanks(thanks: List<Thanks>) {
        thanksDao.deleteAll()
        thanksDao.upsert(*thanks.map { it.toEntity() }.toTypedArray())
    }
}
