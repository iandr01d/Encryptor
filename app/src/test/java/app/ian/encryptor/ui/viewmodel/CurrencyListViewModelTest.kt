package app.ian.encryptor.ui.viewmodel

import app.ian.encryptor.data.CurrencyInfoRepository
import app.ian.encryptor.model.CurrencyInfo
import app.ian.encryptor.model.CurrencyInfoUiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyListViewModelTest {

    @Mock
    private lateinit var repository: CurrencyInfoRepository
    private lateinit var viewModel: CurrencyListViewModel
    private lateinit var uiState: StateFlow<CurrencyInfoUiState>
    private lateinit var itemClicked: Flow<CurrencyInfo>

    private val currencyInfoList = arrayListOf<CurrencyInfo>()

    @ExperimentalCoroutinesApi
    private val dispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = CurrencyListViewModel(repository)

        val currencyInfo = CurrencyInfo(id = "CRO", name = "Crypto.com Chain", symbol = "CRO")
        val currencyInfo2 = CurrencyInfo(id = "BTC", name = "Bitcoin", symbol = "BTC")
        val currencyInfo3 = CurrencyInfo(id = "XRP", name = "XRP", symbol = "XRP")
        currencyInfoList.add(currencyInfo)
        currencyInfoList.add(currencyInfo2)
        currencyInfoList.add(currencyInfo3)

        uiState = viewModel.uiState
        itemClicked = viewModel.itemClicked
    }

    @Test
    fun testGetAllCurrencies() = runBlocking {
        val flow = flow<List<CurrencyInfo>> {
            emit(currencyInfoList)
        }
        Mockito.`when`(repository.getAllCurrencies(false)).thenReturn(flow)

        viewModel.getAllCurrencies(false)
        val emittedCurrencyInfoList = uiState.value.currencyInfoList
        assert(
            emittedCurrencyInfoList.containsAll(currencyInfoList) && currencyInfoList.containsAll(
                emittedCurrencyInfoList
            )
        )
    }

    @Test
    fun testGetAllSortedCurrencies() = runBlocking {
        val sortedCurrencyInfoList = currencyInfoList.sortedBy { it.name }
        val flow = flow {
            emit(sortedCurrencyInfoList)
        }
        Mockito.`when`(repository.getAllCurrencies(true)).thenReturn(flow)

        viewModel.getAllCurrencies(true)
        val emittedCurrencyInfoList = uiState.value.currencyInfoList
        var isSorted = true

        emittedCurrencyInfoList.forEachIndexed { i, value ->
            val element = sortedCurrencyInfoList[i]
            if (element.name != value.name) {
                isSorted = false
            }
        }
        assert(isSorted)
    }

    @Test
    fun testInsertAllCurrencies() = runBlocking {
        val flow = flow<List<CurrencyInfo>> {
            emit(currencyInfoList)
        }
        Mockito.`when`(repository.getAllCurrencies(false)).thenReturn(flow)

        viewModel.insertAllCurrencies(currencyInfoList)
        viewModel.getAllCurrencies(false)
        assert(uiState.value.currencyInfoList.size == currencyInfoList.size)
    }

    @Test
    fun testDeleteAllCurrencies() = runBlocking {
        val flow = flow<List<CurrencyInfo>> {
            emit(arrayListOf())
        }
        Mockito.`when`(repository.getAllCurrencies(false)).thenReturn(flow)

        viewModel.deleteAllCurrencies()
        viewModel.getAllCurrencies(false)
        assertEquals(0, uiState.value.currencyInfoList.size)
    }

    @Test
    fun testOnItemClicked() = runBlocking {
        val currencyInfo = CurrencyInfo(id = "CRO", name = "Crypto.com Chain", symbol = "CRO")
        viewModel.onItemClicked(currencyInfo)
        itemClicked.take(1).collect {
            assert(it == currencyInfo)
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}