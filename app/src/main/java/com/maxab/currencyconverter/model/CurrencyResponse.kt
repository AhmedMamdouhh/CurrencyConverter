package com.maxab.currencyconverter.model

data class CurrencyResponse(
    var base: String,
    var results: HashMap<String, Double> = HashMap(),
    var result: HashMap<String, Double> = HashMap(),
    var updated: String,
    var ms: Int
)