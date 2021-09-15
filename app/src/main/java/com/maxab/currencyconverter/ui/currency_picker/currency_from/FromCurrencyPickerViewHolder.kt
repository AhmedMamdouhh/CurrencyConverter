package com.maxab.currencyconverter.ui.currency_picker.currency_from

import androidx.recyclerview.widget.RecyclerView
import com.maxab.currencyconverter.databinding.ItemFromCurrencyBinding
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.ui.currency_picker.CurrencyPickerViewModel

class FromCurrencyPickerViewHolder(
    private val currencyBinding: ItemFromCurrencyBinding,
    private val currencyPickerViewModel: CurrencyPickerViewModel
)
    : RecyclerView.ViewHolder(currencyBinding.root) {

    fun bind(currencyFrom: CurrencyEntity) {
        currencyBinding.currencyObject = currencyFrom
        currencyBinding.currencyListener = currencyPickerViewModel
    }
}