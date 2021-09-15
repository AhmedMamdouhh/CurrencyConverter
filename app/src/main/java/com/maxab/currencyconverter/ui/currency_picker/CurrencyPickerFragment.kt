package com.maxab.currencyconverter.ui.currency_picker

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxab.currencyconverter.R
import com.maxab.currencyconverter.databinding.FragmentCurrencyPickerBinding
import com.maxab.currencyconverter.manager.base.MainActivity
import com.maxab.currencyconverter.manager.utilities.Constants
import com.maxab.currencyconverter.manager.utilities.EventObserver
import com.maxab.currencyconverter.manager.utilities.recyclerAnimationExtension
import com.maxab.currencyconverter.manager.utilities.statusBarColor
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
    //TODO Inject adapters and dialog:
    private lateinit var toCurrencyPickerAdapter: ToCurrencyPickerAdapter
    private lateinit var fromCurrencyPickerAdapter: FromCurrencyPickerAdapter
    private lateinit var dialog : Dialog
    private var dataMode = Constants.MODE_ONLINE

    @Inject lateinit var currencyEntity:CurrencyEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        currencyPickerBinding = FragmentCurrencyPickerBinding.inflate(inflater, container, false)
        currencyPickerBinding.currencyPickerListener = currencyPickerViewModel

        initializeBaseCurrency()

        observeCurrencyFullList()
        observeCurrencyFullListOffline()
        observeFromCurrencyPickerClicked()
        observeFromDialogClicked()
        observeToCurrencySelected()
        observeSwitchOnline()
        observeSwitchOffline()

        return currencyPickerBinding.root
    }


    private fun initializeBaseCurrency(){
        currencyEntity.currencyName= getString(R.string.currency_picker_base_currency_name)
        currencyEntity.currencySymbol = getString(R.string.currency_picker_base_currency_symbol)
        currencyEntity.currencyCode = getString(R.string.currency_picker_base_currency_code)
        currencyPickerBinding.currencyObject = currencyEntity
    }

    private fun observeSwitchOnline(){
        currencyPickerViewModel.observeSwitchOnline.observe(viewLifecycleOwner,EventObserver{
            currencyPickerBinding.cvCurrencyPickerBaseCurrency.isClickable = true
            currencyPickerBinding.ivCurrencyPickerLock.visibility = GONE
            dataMode = Constants.MODE_ONLINE
            currencyPickerBinding.ivCurrencyPickerTopCloud.setImageResource(R.drawable.ic_wave_top_light)
            currencyPickerBinding.ivCurrencyPickerBottomCloud.setImageResource(R.drawable.ic_wave_bottom_light)
            (activity as MainActivity).statusBarColor(R.color.colorPrimaryLight)
        })
    }

    private fun observeSwitchOffline(){
        currencyPickerViewModel.observeSwitchOffline.observe(viewLifecycleOwner,EventObserver{
            currencyPickerBinding.cvCurrencyPickerBaseCurrency.isClickable = false
            currencyPickerBinding.ivCurrencyPickerLock.visibility = VISIBLE
            initializeBaseCurrency()
            dataMode = Constants.MODE_OFFLINE
            currencyPickerBinding.ivCurrencyPickerTopCloud.setImageResource(R.drawable.ic_wave_top_dark)
            currencyPickerBinding.ivCurrencyPickerBottomCloud.setImageResource(R.drawable.ic_wave_bottom_dark)
            (activity as MainActivity).statusBarColor(R.color.colorLightGrey)
        })
    }

    private fun observeToCurrencySelected() {
        currencyPickerViewModel.observeOnToCurrencySelected.observe(viewLifecycleOwner,EventObserver{currencyObject->
            val action = CurrencyPickerFragmentDirections.actionCurrencyPickerToCurrencyConverterDialog(
                currencyObject,currencyPickerBinding.currencyObject,dataMode)
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

    private fun observeCurrencyFullListOffline() {
        currencyPickerViewModel.observeCurrencyFullListOffline.observe(viewLifecycleOwner,EventObserver{
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