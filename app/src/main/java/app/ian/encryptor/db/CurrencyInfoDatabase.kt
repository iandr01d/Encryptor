package app.ian.encryptor.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.ian.encryptor.model.CurrencyInfo

@Database(entities = [CurrencyInfo::class], version = 1, exportSchema = false)
abstract class CurrencyInfoDatabase : RoomDatabase() {
    abstract fun currencyInfoDao(): CurrencyInfoDao
}