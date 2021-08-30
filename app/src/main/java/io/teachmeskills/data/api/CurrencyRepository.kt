package io.teachmeskills.data.api

import io.teachmeskills.data.api.restApi.CurrencyApi

class CurrencyRepository(
    private val currencyApi: CurrencyApi
) {
    suspend fun getInfoAboutCurrencyLimit(limit: Int) : List<Currency> =
        currencyApi.getCurrencyList(limit).data.map {
            Currency(id = it.id, name = it.name, symbol = it.symbol)
        }

}