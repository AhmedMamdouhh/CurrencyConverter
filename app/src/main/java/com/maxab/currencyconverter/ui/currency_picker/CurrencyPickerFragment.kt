package com.maxab.currencyconverter.ui.currency_picker

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxab.currencyconverter.R
import com.maxab.currencyconverter.databinding.FragmentCurrencyPickerBinding
import com.maxab.currencyconverter.manager.utilities.EventObserver
import com.maxab.currencyconverter.manager.utilities.recyclerAnimationExtension
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.ui.currency_picker.currency_from.FromCurrencyPickerAdapter
import com.maxab.currencyconverter.ui.currency_picker.currency_to.ToCurrencyPickerAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class CurrencyPickerFragment : Fragment() {

    private lateinit var currencyPickerBinding: FragmentCurrencyPickerBinding
    private val currencyPickerViewModel: CurrencyPickerViewModel by viewModels()
    private lateinit var toCurrencyPickerAdapter: ToCurrencyPickerAdapter
    private lateinit var fromCurrencyPickerAdapter: FromCurrencyPickerAdapter
    private lateinit var dialog : Dialog
    @Inject
    lateinit var currencyEntity:CurrencyEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        currencyPickerBinding = FragmentCurrencyPickerBinding.inflate(inflater, container, false)
        currencyPickerBinding.currencyPickerListener = currencyPickerViewModel

        initializeBaseCurrency()

        observeCurrencyFullList()
        observeFromCurrencyPickerClicked()
        observeFromDialogClicked()
        observeToCurrencySelected()


        return currencyPickerBinding.root
    }

    private fun initializeBaseCurrency(){
        currencyEntity.currencyName= getString(R.string.currency_picker_base_currency_name)
        currencyEntity.currencySymbol = getString(R.string.currency_picker_base_currency_symbol)
        currencyPickerBinding.currencyObject = currencyEntity
    }

    private fun observeToCurrencySelected() {
        currencyPickerViewModel.observeOnToCurrencySelected.observe(viewLifecycleOwner,EventObserver{currencyObject->
            val action = CurrencyPickerFragmentDirections.actionCurrencyPickerToCurrencyConverterDialog(currencyObject,currencyPickerBinding.currencyObject)
            findNavController().navigate(action)
        })
    }


    private fun observeFromDialogClicked() {
        currencyPickerViewModel.observeOnFromDialogClicked.observe(viewLifecycleOwner,EventObserver{
            currencyPickerBinding.currencyObject = it
            dialog.hide()
        })
    }

    private fun observeFromCurrencyPickerClicked() {
        currencyPickerViewModel.observeOnFromCurrencyClicked.observe(viewLifecycleOwner,EventObserver{
            dialog.show()
        })
    }

    private fun observeCurrencyFullList() {
        currencyPickerViewModel.observeCurrencyFullList.observe(viewLifecycleOwner, EventObserver {
            initializeFromCurrencyList(it)
            initializeToCurrencyList(it)
        })
    }


    private fun initializeToCurrencyList(currencyList:ArrayList<CurrencyEntity>){
        toCurrencyPickerAdapter = ToCurrencyPickerAdapter(currencyList, currencyPickerViewModel)
        currencyPickerBinding.rvToCurrencyPickerList.layoutManager =
            LinearLayoutManager(requireContext())
        currencyPickerBinding.rvToCurrencyPickerList.adapter = toCurrencyPickerAdapter
        recyclerAnimationExtension(context, currencyPickerBinding.rvToCurrencyPickerList)
    }

    private fun initializeFromCurrencyList(currencyList:ArrayList<CurrencyEntity>){

        //Initialize dialog:
        dialog = Dialog(requireActivity())
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Objects.requireNonNull<Window>(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_from_currency_picker)

        //initialize RecyclerView
        val recyclerView = dialog.findViewById<RecyclerView>(R.id.rv_from_currency_picker_list)
        fromCurrencyPickerAdapter = FromCurrencyPickerAdapter(currencyList,currencyPickerViewModel)
        recyclerView.adapter = fromCurrencyPickerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


    }

}