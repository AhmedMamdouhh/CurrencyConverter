package com.maxab.currencyconverter.repository

import com.maxab.currencyconverter.manager.connection.Api
import com.maxab.currencyconverter.manager.connection.BaseDataSource
import com.maxab.currencyconverter.manager.utilities.getCurrencyRateExtension
import javax.inject.Inject

class CurrencyConverterRepository @Inject constructor(
    private val retrofitApi: Api,
) : BaseDataSource() {

    suspend fun convertTwoCurrencies(fromCurrency: String, toCurrency: String, amount: Double) =
        getCurrencyRateExtension(safeApiCall {
            retrofitApi.convertCurrencies(from = fromCurrency, to = toCurrency, amount = amount)
        })
}