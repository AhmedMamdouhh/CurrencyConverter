package com.maxab.currencyconverter.ui.currency_picker.currency_from

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxab.currencyconverter.databinding.ItemFromCurrencyBinding
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.ui.currency_picker.CurrencyPickerViewModel

class FromCurrencyPickerAdapter(
    private val currencyToList: ArrayList<CurrencyEntity>,
    private val currencyPickerViewModel: CurrencyPickerViewModel
) : RecyclerView.Adapter<FromCurrencyPickerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FromCurrencyPickerViewHolder {
        return FromCurrencyPickerViewHolder(
            ItemFromCurrencyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), currencyPickerViewModel
        )
    }

    override fun onBindViewHolder(holderToCurrency: FromCurrencyPickerViewHolder, position: Int) {
        holderToCurrency.bind(currencyToList[position])
    }

    override fun getItemCount(): Int {
        return currencyToList.size
    }
}