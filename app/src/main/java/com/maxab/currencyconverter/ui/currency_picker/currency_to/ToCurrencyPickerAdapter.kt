package com.maxab.currencyconverter.ui.currency_picker.currency_to

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxab.currencyconverter.databinding.ItemToCurrencyBinding
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.ui.currency_picker.CurrencyPickerViewModel

class ToCurrencyPickerAdapter(
    private val currencyToList: ArrayList<CurrencyEntity>,
    private val currencyPickerViewModel: CurrencyPickerViewModel
) : RecyclerView.Adapter<ToCurrencyPickerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToCurrencyPickerViewHolder {
        return ToCurrencyPickerViewHolder(
            ItemToCurrencyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), currencyPickerViewModel
        )
    }

    override fun onBindViewHolder(holderToCurrency: ToCurrencyPickerViewHolder, position: Int) {
        holderToCurrency.bind(currencyToList[position])
    }

    override fun getItemCount(): Int {
        return currencyToList.size
    }
}