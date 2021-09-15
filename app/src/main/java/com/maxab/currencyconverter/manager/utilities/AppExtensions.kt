package com.maxab.currencyconverter.manager.utilities

import android.content.Context
import android.os.Build
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.RecyclerView
import com.maxab.currencyconverter.R
import com.maxab.currencyconverter.manager.connection.Resource
import com.maxab.currencyconverter.model.CurrencyEntity
import com.maxab.currencyconverter.model.CurrencyResponse
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
            currencyEntity.currencyRate = currencyRate
            currencyEntity.currencyName = pound.displayName
            currencyEntity.currencyCode = currencyCode
            currencyEntity.currencySymbol = pound.symbol
            return currencyEntity
        }
    }
    return null
}


fun recyclerAnimationExtension(context: Context?, recyclerView: RecyclerView) {
    val resId: Int = R.anim.layout_animation
    val animation: LayoutAnimationController =
        AnimationUtils.loadLayoutAnimation(context, resId)
    recyclerView.layoutAnimation = animation
}

