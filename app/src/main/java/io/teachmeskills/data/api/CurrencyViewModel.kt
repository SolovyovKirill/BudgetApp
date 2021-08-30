package io.teachmeskills.data.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val liveDataCur: MutableLiveData<List<Currency>> = MutableLiveData()

    fun getInfoAboutCurrency(limit: Int) {
        viewModelScope.launch {
            val currencyList = withContext(Dispatchers.IO) {
                currencyRepository.getInfoAboutCurrencyLimit(limit)
            }
            liveDataCur.value = currencyList
        }

    }
}