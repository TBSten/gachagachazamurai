package me.tbsten.gachagachazamurai.data.thanks

import me.tbsten.gachagachazamurai.database.AppDatabase
import me.tbsten.gachagachazamurai.database.thanks.ThanksDao
import me.tbsten.gachagachazamurai.database.thanks.toEntity
import me.tbsten.gachagachazamurai.database.thanks.toThanks
import me.tbsten.gachagachazamurai.domain.Thanks
import me.tbsten.gachagachazamurai.file.AppFileSource
import javax.inject.Inject

class ThanksRepository @Inject constructor(
    private val db: AppDatabase,
    private val fileSource: AppFileSource,
) {
    private val thanksDao: ThanksDao
        get() = db.thanksDao()

    suspend fun getThanks() = thanksDao.getAll().map { it.toThanks() }
    suspend fun saveThanks(thanks: List<Thanks>) {
        val saveTargetThanks = thanks.map {
            var saveTarget = it
            if (it.image.scheme == "content") {
                val imageFileName = it.image.hashCode().toString(36)
                val savedImageUri = fileSource.save(it.image, imageFileName)
                saveTarget = it.copy(image = savedImageUri)
            }
            saveTarget
        }
        thanksDao.deleteAll()
        thanksDao.upsert(*saveTargetThanks.map { it.toEntity() }.toTypedArray())
    }

    suspend fun saveThanks(thanks: Thanks) {
        var saveTargetThanks = thanks
        if (thanks.image.scheme == "content") {
            val imageFileName = thanks.image.hashCode().toString(36)
            val savedImageUri = fileSource.save(thanks.image, imageFileName)
            saveTargetThanks = thanks.copy(image = savedImageUri)
        }
        thanksDao.upsert(saveTargetThanks.toEntity())
    }

    suspend fun deleteThanks(thanks: Thanks) {
        thanksDao.delete(thanks.toEntity())
    }
}
