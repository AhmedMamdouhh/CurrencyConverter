package com.maxab.currencyconverter.ui.currency_picker.currency_to

import androidx.recyclerview.widget.RecyclerView
import com.maxab.currencyconverter.databinding.ItemToCurrencyBinding
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.ui.currency_picker.CurrencyPickerViewModel

class ToCurrencyPickerViewHolder(
    private val currencyBinding: ItemToCurrencyBinding,
    private val currencyPickerViewModel: CurrencyPickerViewModel
) : RecyclerView.ViewHolder(currencyBinding.root) {

    fun bind(currencyTo: CurrencyEntity) {
        currencyBinding.currencyObject = currencyTo
        currencyBinding.currencyListener = currencyPickerViewModel
    }
}