package com.maxab.currencyconverter.manager.connection

object ApiEndPoints {

    const val BASE_URL = "https://api.fastforex.io/"
    const val API_KEY = "39c5df1eb7-7c5b0c5341-qze7zz"


    const val GET_CURRENCIES = "${BASE_URL}fetch-all"
    const val CONVERT_CURRENCIES = "${BASE_URL}convert"
}