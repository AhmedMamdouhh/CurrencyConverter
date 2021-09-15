package com.maxab.currencyconverter.ui.currency_converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.maxab.currencyconverter.databinding.DialogCurrencyConverterBinding
import com.maxab.currencyconverter.manager.base.BaseBottomSheet
import com.maxab.currencyconverter.manager.utilities.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConverterDialog: BaseBottomSheet() {

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
        currencyConverterBinding.dataMode = args.dataMode

        observeConverterResult()
        observeCloseClicked()

        return currencyConverterBinding.root
    }

    private fun observeCloseClicked() {
        currencyConverterViewModel.observeOnCloseClicked.observe(viewLifecycleOwner,EventObserver{
            closeSheet()
        })
    }

    private fun observeConverterResult() {
        currencyConverterViewModel.observeConvertRate.observe(viewLifecycleOwner,EventObserver{
            currencyConverterBinding.cvCurrencyConverterResultContainer.visibility = VISIBLE
            currencyConverterBinding.tvCurrencyConverterResult.text = it.toString()
        })
    }


}