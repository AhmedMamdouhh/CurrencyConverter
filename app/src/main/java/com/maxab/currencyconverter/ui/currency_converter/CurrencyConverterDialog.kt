package com.maxab.currencyconverter.ui.currency_converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.maxab.currencyconverter.databinding.DialogCurrencyConverterBinding
import com.maxab.currencyconverter.manager.base.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterDialog:BaseBottomSheet() {

    private lateinit var currencyConverterBinding: DialogCurrencyConverterBinding
    private val currencyConverterViewModel: CurrencyConverterViewModel by viewModels()
    private val args: CurrencyConverterDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        currencyConverterBinding = DialogCurrencyConverterBinding.inflate(inflater, container, false)
        currencyConverterBinding.toCurrencyObject = args.toCurrencyObject
        currencyConverterBinding.fromCurrencyObject = args.fromCurrencyObject
        currencyConverterBinding.currencyConverterListener=currencyConverterViewModel

        return currencyConverterBinding.root
    }
}