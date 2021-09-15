package com.maxab.currencyconverter.dp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maxab.currencyconverter.model.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencyEntity: CurrencyEntity)

    @Query("SELECT * FROM currency_table")
    fun getAllCurrencies(): List<CurrencyEntity>
}