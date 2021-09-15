package com.maxab.currencyconverter.manager.utilities

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.RecyclerView
import com.maxab.currencyconverter.R
import com.maxab.currencyconverter.manager.base.MainActivity
import com.maxab.currencyconverter.manager.connection.Resource
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.model.CurrencyResponse
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

fun getCurrenciesListExtension(currencyResponse: Resource<CurrencyResponse>)
        : ArrayList<CurrencyEntity> {
    val map: Map<String, Double> = currencyResponse.data?.results!!
    val currencyList = arrayListOf<CurrencyEntity>()

    map.forEach {
        currencyList.add(fillCurrencyData(it.key, it.value)!!)
    }

    return currencyList
}

private fun fillCurrencyData(
    currencyCode: String,
    currencyRate: Double
): CurrencyEntity? {
    val currencyEntity = CurrencyEntity()
    if (currencyCode.isNotEmpty()) {
        val pound = Currency.getInstance(currencyCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pound.getSymbol(Locale.getDefault(Locale.Category.DISPLAY))

            val decimalFormat = DecimalFormat("#.######")
            val y = decimalFormat.format(currencyRate)
            Log.e("sdfdsfsdf", "fillCurrencyData: $y")
            currencyEntity.currencyRate = y
            currencyEntity.currencyName = pound.displayName
            currencyEntity.currencyCode = currencyCode
            currencyEntity.currencySymbol = pound.symbol

            return currencyEntity
        }
    }
    return null
}

fun getCurrencyRateExtension(currencyResponse: Resource<CurrencyResponse>): Double {
    val map: Map<String, Double> = currencyResponse.data?.result!!
    var rate = 0.0

    map.forEach {
        if (it.key != "rate")
            rate =  it.value
    }

    return rate
}

fun recyclerAnimationExtension(context: Context?, recyclerView: RecyclerView) {
    val resId: Int = R.anim.layout_animation
    val animation: LayoutAnimationController =
        AnimationUtils.loadLayoutAnimation(context, resId)
    recyclerView.layoutAnimation = animation
}

fun MainActivity.statusBarColor(color: Int) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
     window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      window.statusBarColor =
            getColor(color)
    }
}


