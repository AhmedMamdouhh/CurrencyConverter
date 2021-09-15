package com.maxab.currencyconverter.repository

import android.util.Log
import com.maxab.currencyconverter.dp.CurrencyDao
import com.maxab.currencyconverter.manager.connection.*
import com.maxab.currencyconverter.manager.utilities.getCurrenciesListExtension
import com.maxab.currencyconverter.model.CurrencyEntity
import javax.inject.Inject


class CurrencyPickerRepository @Inject constructor(
    private val retrofitApi: Api,
    private val currencyDao: CurrencyDao
) : BaseDataSource() {


    suspend fun getCurrencyList(baseCurrency: String) =
        safeApiCall { retrofitApi.getCurrenciesFullList(baseCurrency = baseCurrency) }

    suspend fun insertCurrenciesToDatabase(currencyEntity: CurrencyEntity){
        currencyDao.insertCurrencies(currencyEntity)
    }

    fun getCurrenciesListOffline() = currencyDao.getAllCurrencies()

}