package app.ian.encryptor.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.ian.encryptor.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyInfoDao {
    @Query("SELECT * FROM currency")
    fun getAllCurrencies(): Flow<List<CurrencyInfo>>

    @Query("SELECT * FROM currency ORDER BY name ASC")
    fun getAllSortedCurrencies(): Flow<List<CurrencyInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencies(currencies: List<CurrencyInfo>)

    @Query("DELETE FROM currency")
    suspend fun deleteAllCurrencies()
}