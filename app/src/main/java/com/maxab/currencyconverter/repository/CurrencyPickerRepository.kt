package com.maxab.currencyconverter.repository

import com.maxab.currencyconverter.manager.connection.*
import com.maxab.currencyconverter.manager.utilities.getCurrenciesListExtension
import javax.inject.Inject


class CurrencyPickerRepository @Inject constructor(
    private val retrofitApi: Api
) : BaseDataSource() {


    suspend fun getCurrencyList(baseCurrency: String) =
        getCurrenciesListExtension(safeApiCall { retrofitApi.getCurrenciesFullList(baseCurrency = baseCurrency) })
}