package app.ian.encryptor.data

import app.ian.encryptor.db.CurrencyInfoDatabase
import app.ian.encryptor.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyInfoRepository @Inject constructor(
    private val currencyInfoDatabase: CurrencyInfoDatabase
) {

    fun getAllCurrencies(shouldSort: Boolean): Flow<List<CurrencyInfo>> =
        if (shouldSort)
            currencyInfoDatabase.currencyInfoDao().getAllSortedCurrencies()
        else
            currencyInfoDatabase.currencyInfoDao().getAllCurrencies()

    suspend fun insertAllCurrencies(currencies: List<CurrencyInfo>) {
        currencyInfoDatabase.currencyInfoDao().insertAllCurrencies(currencies)
    }

    suspend fun deleteAllCurrencies() {
        currencyInfoDatabase.currencyInfoDao().deleteAllCurrencies()
    }
}