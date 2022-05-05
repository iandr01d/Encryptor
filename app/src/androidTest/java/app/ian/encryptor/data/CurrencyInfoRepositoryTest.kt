package app.ian.encryptor.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ian.encryptor.db.CurrencyInfoDatabaseTest
import app.ian.encryptor.model.CurrencyInfo
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.collect
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencyInfoRepositoryTest : CurrencyInfoDatabaseTest() {

    private lateinit var repository: CurrencyInfoRepository

    private val currencyInfoList = arrayListOf<CurrencyInfo>()

    @Before
    fun setup() {
        repository = CurrencyInfoRepository(appDatabase)

        val currencyInfo = CurrencyInfo(id = "CRO", name = "Crypto.com Chain", symbol = "CRO")
        val currencyInfo2 = CurrencyInfo(id = "BTC", name = "Bitcoin", symbol = "BTC")
        val currencyInfo3 = CurrencyInfo(id = "XRP", name = "XRP", symbol = "XRP")
        currencyInfoList.add(currencyInfo)
        currencyInfoList.add(currencyInfo2)
        currencyInfoList.add(currencyInfo3)
    }

    @Test
    fun testGetAllCurrencies() = runBlocking {
        repository.insertAllCurrencies(currencyInfoList)
        repository.getAllCurrencies(false).take(1).collect {
            assert(it.containsAll(currencyInfoList) && currencyInfoList.containsAll(it))
        }
    }

    @Test
    fun testInsertAllCurrencies() = runBlocking {
        repository.insertAllCurrencies(currencyInfoList)
        assertEquals(3, repository.getAllCurrencies(false).first().size)
    }

    @Test
    fun testDeleteAllCurrencies() = runBlocking {
        repository.deleteAllCurrencies()
        assertEquals(0, repository.getAllCurrencies(false).first().size)
    }
}