package com.maxab.currencyconverter.ui.currency_converter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxab.currencyconverter.manager.ResponseManager
import com.maxab.currencyconverter.repository.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository,
    private val responseManager: ResponseManager
) : ViewModel() {

    init {
        getCurrencyRate()
    }

    private fun getCurrencyRate() {
        responseManager.loading()
        viewModelScope.launch {

            val s = currencyConverterRepository.convertTwoCurrencies("USD", "EGP", 123.0)
            val map: Map<String, Double> = s.data?.result!!

            Log.e("sdfdsf", "getCurrencyRate: " )

            responseManager.hideLoading()
        }
    }

}