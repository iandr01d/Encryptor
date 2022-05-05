package app.ian.encryptor.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.ian.encryptor.model.CurrencyInfo
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class CurrencyInfoDaoTest : CurrencyInfoDatabaseTest() {

    private val currencyInfoList = arrayListOf<CurrencyInfo>()

    @Before
    fun setup() {
        val currencyInfo = CurrencyInfo(id = "CRO", name = "Crypto.com Chain", symbol = "CRO")
        val currencyInfo2 = CurrencyInfo(id = "BTC", name = "Bitcoin", symbol = "BTC")
        val currencyInfo3 = CurrencyInfo(id = "XRP", name = "XRP", symbol = "XRP")
        currencyInfoList.add(currencyInfo)
        currencyInfoList.add(currencyInfo2)
        currencyInfoList.add(currencyInfo3)
    }

    @Test
    fun testGetAllCurrencies() = runBlocking {
        appDatabase.currencyInfoDao().insertAllCurrencies(currencyInfoList)
        val dbList = appDatabase.currencyInfoDao().getAllCurrencies().first()
        assert(dbList.containsAll(currencyInfoList) && currencyInfoList.containsAll(dbList))
    }

    @Test
    fun testInsertAllCurrencies() = runBlocking {
        appDatabase.currencyInfoDao().insertAllCurrencies(currencyInfoList)
        assertEquals(3, appDatabase.currencyInfoDao().getAllCurrencies().first().size)
    }

    @Test
    fun testDeleteAllCurrencies() = runBlocking {
        appDatabase.currencyInfoDao().deleteAllCurrencies()
        assertEquals(0, appDatabase.currencyInfoDao().getAllCurrencies().first().size)
    }

    @Test
    fun testGetAllSortedCurrencies() = runBlocking {
        appDatabase.currencyInfoDao().insertAllCurrencies(currencyInfoList)

        val list = currencyInfoList.sortedBy { it.name }
        val dbList = appDatabase.currencyInfoDao().getAllSortedCurrencies().first()
        var isSorted = true

        list.forEachIndexed { i, value ->
            val element = dbList[i]
            if (element.name != value.name) {
                isSorted = false
            }
        }
        assert(isSorted)
    }
}