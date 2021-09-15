package com.maxab.currencyconverter.manager.utilities

import com.maxab.currencyconverter.model.CurrencyEntity

fun getCurrencyListOfflineExtension(): ArrayList<CurrencyEntity> {

    val currencyEntityList = ArrayList<CurrencyEntity>()

    currencyEntityList.add(createCurrencyEntity("Egyptian Pound", "EGP", "EGP", "18.56843"))
    currencyEntityList.add(createCurrencyEntity("British Pound", "GBP", "£",  "0.85442"))
    currencyEntityList.add(createCurrencyEntity("Afghan Afghani", "AFN", "AFN", "100.61774"))
    currencyEntityList.add(createCurrencyEntity("Swazi Lilangeni", "SZL", "SZL", "17.05184"))
    currencyEntityList.add(createCurrencyEntity("Sri Lankan Rupee", "LKR", "LKR", "236.03243"))
    currencyEntityList.add(createCurrencyEntity("Lesotho Loti", "LSL", "LSL", "16.89437"))
    currencyEntityList.add(createCurrencyEntity("United Arab Emirates Dirham", "AED", "AED", "4.34025"))
    currencyEntityList.add(createCurrencyEntity("Armenian Dram", "AMD", "AMD", "574.84257"))
    currencyEntityList.add(createCurrencyEntity("Swiss Franc", "CHF", "CHF", "1.08617"))
    currencyEntityList.add(createCurrencyEntity("US Dollar", "$", "USD", "1.18202"))


    return currencyEntityList
}

private fun createCurrencyEntity(
    currencyName: String, currencyCode: String, currencySymbol: String, currencyRate: String
): CurrencyEntity {

    val currencyEntity = CurrencyEntity()

    currencyEntity.currencyName = currencyName
    currencyEntity.currencyCode = currencyCode
    currencyEntity.currencySymbol = currencySymbol
    currencyEntity.currencyRate = currencyRate

    return currencyEntity
}