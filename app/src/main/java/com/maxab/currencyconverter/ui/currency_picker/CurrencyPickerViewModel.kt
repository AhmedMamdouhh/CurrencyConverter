package com.maxab.currencyconverter.ui.currency_picker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxab.currencyconverter.manager.base.ResponseManager
import com.maxab.currencyconverter.manager.utilities.Constants
import com.maxab.currencyconverter.manager.utilities.Event
import com.maxab.currencyconverter.manager.utilities.getCurrenciesListExtension
import com.maxab.currencyconverter.manager.utilities.getCurrencyListOfflineExtension
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.repository.CurrencyPickerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyPickerViewModel @Inject constructor(
    private val currencyPickerRepository: CurrencyPickerRepository,
    private val responseManager: ResponseManager
) : ViewModel() {

    private val _observeCurrencyFullList = MutableLiveData<Event<ArrayList<CurrencyEntity>>>()
    private val _observeOnFromCurrencyClicked = MutableLiveData<Event<Boolean>>()
    private val _observeOnFromDialogClicked = MutableLiveData<Event<CurrencyEntity>>()
    private val _observeOnToCurrencySelected = MutableLiveData<Event<CurrencyEntity>>()
    private val _observeCurrencyFullListOffline = MutableLiveData<Event<ArrayList<CurrencyEntity>>>()
    private val _observeSwitchOnline = MutableLiveData<Event<Boolean>>()
    private val _observeSwitchOffline = MutableLiveData<Event<Boolean>>()


    init {
        responseManager.loading()
        getCurrencyList(Constants.BASE_CURRENCY)
        insertCurrencyListOffline()
    }

    private fun getCurrencyList(baseCurrency: String) {
        responseManager.loading()
        viewModelScope.launch {
            val response = currencyPickerRepository.getCurrencyList(baseCurrency)
            responseManager.hideLoading()
            if (response.data != null) {
                _observeCurrencyFullList.value =
                    Event(getCurrenciesListExtension(response))
            }else
                responseManager.noConnection()
        }
    }

    private fun insertCurrencyListOffline() {
        viewModelScope.launch {
            val currencyOfflineList = getCurrencyListOfflineExtension()
            for (entity in currencyOfflineList)
                currencyPickerRepository.insertCurrenciesToDatabase(entity)
        }
    }

    private fun getCurrencyListOffline() {
        _observeCurrencyFullListOffline.value =
            Event(currencyPickerRepository.getCurrenciesListOffline() as ArrayList<CurrencyEntity>)
    }

    //Clicks:
    fun onFromCurrencyDialogClicked(currencyObject: CurrencyEntity) {
        getCurrencyList(currencyObject.currencyCode)
        _observeOnFromDialogClicked.value = Event(currencyObject)
    }
    fun onToCurrencySelected(currencyObject: CurrencyEntity) { _observeOnToCurrencySelected.value = Event(currencyObject) }
    fun onFromCurrencyPickerClicked() { _observeOnFromCurrencyClicked.value = Event(true) }
    fun onSwitchChecked(online: Boolean) {

        if (online) {
            _observeSwitchOnline.value = Event(true)
            getCurrencyList(Constants.BASE_CURRENCY)
        } else {
            _observeSwitchOffline.value = Event(false)
            getCurrencyListOffline()
        }
    }


    //Getters:
    val observeCurrencyFullList: LiveData<Event<ArrayList<CurrencyEntity>>>
        get() = _observeCurrencyFullList
    val observeOnFromCurrencyClicked: LiveData<Event<Boolean>>
        get() = _observeOnFromCurrencyClicked
    val observeOnFromDialogClicked: LiveData<Event<CurrencyEntity>>
        get() = _observeOnFromDialogClicked
    val observeOnToCurrencySelected: LiveData<Event<CurrencyEntity>>
        get() = _observeOnToCurrencySelected
    val observeCurrencyFullListOffline: LiveData<Event<ArrayList<CurrencyEntity>>>
        get() = _observeCurrencyFullListOffline
    val observeSwitchOnline: LiveData<Event<Boolean>>
        get() = _observeSwitchOnline
    val observeSwitchOffline: LiveData<Event<Boolean>>
        get() = _observeSwitchOffline

}