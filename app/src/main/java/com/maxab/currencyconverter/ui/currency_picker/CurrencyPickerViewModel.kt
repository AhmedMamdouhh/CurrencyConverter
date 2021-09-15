package com.maxab.currencyconverter.ui.currency_picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxab.currencyconverter.manager.ResponseManager
import com.maxab.currencyconverter.manager.utilities.Constants
import com.maxab.currencyconverter.manager.utilities.Event
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

    init {
        responseManager.loading()
        getCurrencyList(Constants.BASE_CURRENCY)
    }

    private fun getCurrencyList(baseCurrency: String) {
        viewModelScope.launch {
            _observeCurrencyFullList.value =
                Event(currencyPickerRepository.getCurrencyList(baseCurrency))
            responseManager.hideLoading()
        }
    }

    //Clicks:
    fun onFromCurrencyDialogClicked(currencyObject:CurrencyEntity) {
        getCurrencyList(currencyObject.currencyCode)
        _observeOnFromDialogClicked.value = Event(currencyObject)
    }

    fun onToCurrencySelected(currencyObject:CurrencyEntity) {
        _observeOnToCurrencySelected.value= Event(currencyObject)
    }

    fun onFromCurrencyPickerClicked() {
        _observeOnFromCurrencyClicked.value = Event(true)
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

}