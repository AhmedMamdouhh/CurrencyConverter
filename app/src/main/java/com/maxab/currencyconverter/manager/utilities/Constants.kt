package com.maxab.currencyconverter.manager.utilities

object Constants {

    const val VERY_SMALL_DELAY = 500
    const val DELAY_SMALL = 1000
    const val DELAY_MEDIUM = 1500
    const val DELAY_BIG = 3000

    const val SUCCESS = "success"
    const val FAILED = "failed"
    const val MESSAGE = "message"

    const val MODE_OFFLINE="offline"
    const val MODE_ONLINE="online"

    //API
    const val FROM = "from"
    const val TO = "to"
    const val AMOUNT = "amount"
    const val API_KEY = "api_key"
    const val BASE_CURRENCY = "EUR"

    //sheet:
    const val SUCCESS_SHEET = "successSheet"
    const val ERROR_SHEET = "errorSheet"
    const val NO_CONNECTION_SHEET = "noConnectionSheet"

    //Data Base
    const val CURRENCY_DATA_BASE_NAME = "currency_database"
    const val CURRENCY_TABLE_NAME = "currency_table"

}