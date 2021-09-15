package com.maxab.currencyconverter.manager.connection

import com.maxab.currencyconverter.manager.utilities.Constants
import com.maxab.currencyconverter.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(ApiEndPoints.GET_CURRENCIES)
    suspend fun getCurrenciesFullList(
        @Query(Constants.API_KEY)
        apiKey: String = ApiEndPoints.API_KEY,
        @Query(Constants.FROM)
        baseCurrency: String
    ): Response<CurrencyResponse>

    @GET(ApiEndPoints.CONVERT_CURRENCIES)
    suspend fun convertCurrencies(
        @Query(Constants.API_KEY)
        apiKey: String = ApiEndPoints.API_KEY,
        @Query(Constants.FROM)
        from: String,
        @Query(Constants.TO)
        to: String,
        @Query(Constants.AMOUNT)
        amount: Double
    ): Response<CurrencyResponse>

}