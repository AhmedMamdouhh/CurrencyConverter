package com.maxab.currencyconverter.dp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxab.currencyconverter.model.CurrencyEntity

@Database(
    entities = [CurrencyEntity::class],
    version = 1
)
abstract class CurrencyDataBase :RoomDatabase(){
    abstract fun getCurrencyDao():CurrencyDao
}