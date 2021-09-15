package com.maxab.currencyconverter.ui.currency_converter

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxab.currencyconverter.manager.base.ResponseManager
import com.maxab.currencyconverter.manager.utilities.Constants
import com.maxab.currencyconverter.manager.utilities.Event
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.repository.CurrencyConverterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val currencyConverterRepository: CurrencyConverterRepository,
    private val responseManager: ResponseManager
) : ViewModel() {

    private val _observeOnCloseClicked = MutableLiveData<Event<Boolean>>()
    private val _observeConvertRate = MutableLiveData<Event<Double>>()

    private fun getCurrencyRateOnline(
        fromCurrency: CurrencyEntity,
        toCurrency: CurrencyEntity,
        amount: String,
        dataMode: String
    ) {

        if (dataMode == Constants.MODE_ONLINE) {
            responseManager.loading()

            viewModelScope.launch {
                _observeConvertRate.value =
                    Event(
                        currencyConverterRepository.convertTwoCurrencies(
                            fromCurrency.currencyCode,
                            toCurrency.currencyCode,
                            amount.toDouble()
                        )
                    )
                responseManager.hideLoading()
            }
        } else if (dataMode == Constants.MODE_OFFLINE) {
            _observeConvertRate.value = Event(
                toCurrency.currencyRate.toDouble() * amount.toDouble()
            )
        }
    }

    //Click:
    fun onConvertRateClicked(
        fromCurrency: CurrencyEntity,
        toCurrency: CurrencyEntity,
        amount: EditText,
        dataMode: String
    ) {
        getCurrencyRateOnline(fromCurrency, toCurrency, amount.text.toString(), dataMode)
    }

    fun onCloseClicked() {
        _observeOnCloseClicked.value = Event(true)
    }

    val observeOnCloseClicked: LiveData<Event<Boolean>>
        get() = _observeOnCloseClicked
    val observeConvertRate: LiveData<Event<Double>>
        get() = _observeConvertRate

}