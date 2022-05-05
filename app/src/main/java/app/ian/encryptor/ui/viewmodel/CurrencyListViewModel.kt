package app.ian.encryptor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ian.encryptor.data.CurrencyInfoRepository
import app.ian.encryptor.model.CurrencyInfo
import app.ian.encryptor.model.CurrencyInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val repository: CurrencyInfoRepository
) : ViewModel() {

    private var getAllCurrenciesJob: Job? = null
    private var insertAllCurrenciesJob: Job? = null
    private var deleteAllCurrenciesJob: Job? = null
    private var onItemClickedJob: Job? = null

    private val _uiState = MutableStateFlow(CurrencyInfoUiState(listOf()))
    val uiState: StateFlow<CurrencyInfoUiState> = _uiState.asStateFlow()

    private val _itemClicked =
        MutableSharedFlow<CurrencyInfo>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val itemClicked = _itemClicked.distinctUntilChanged()

    fun getAllCurrencies(shouldSort: Boolean) {
        getAllCurrenciesJob?.cancel()
        getAllCurrenciesJob = viewModelScope.launch {
            repository.getAllCurrencies(shouldSort).collect {
                _uiState.update { uiState ->
                    uiState.copy(currencyInfoList = it)
                }
            }
        }
    }

    suspend fun insertAllCurrencies(currencies: List<CurrencyInfo>) {
        insertAllCurrenciesJob?.cancel()
        insertAllCurrenciesJob = viewModelScope.launch {
            repository.insertAllCurrencies(currencies)
                .also { getAllCurrencies(false) }
        }
    }

    suspend fun deleteAllCurrencies() {
        deleteAllCurrenciesJob?.cancel()
        deleteAllCurrenciesJob = viewModelScope.launch {
            repository.deleteAllCurrencies()
        }
    }

    fun onItemClicked(currencyInfo: CurrencyInfo) {
        onItemClickedJob?.cancel()
        onItemClickedJob = viewModelScope.launch {
            _itemClicked.emit(currencyInfo)
        }
    }
}