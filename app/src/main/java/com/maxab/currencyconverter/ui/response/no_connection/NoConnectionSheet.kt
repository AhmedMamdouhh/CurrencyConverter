package com.maxab.currencyconverter.ui.response.no_connection

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maxab.currencyconverter.R
import com.maxab.currencyconverter.databinding.LayoutNoConnectionBinding
import com.maxab.currencyconverter.manager.base.BaseBottomSheet

class NoConnectionSheet : BaseBottomSheet() {
    private lateinit var noConnectionBinding: LayoutNoConnectionBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        noConnectionBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_no_connection, container, false)
        noConnectionBinding.lifecycleOwner = this
        noConnectionBinding.clickListener = this

        return noConnectionBinding.root
    }

    fun onCloseClicked() {
        closeSheet()
    }


}

