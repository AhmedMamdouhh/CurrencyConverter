package com.maxab.currencyconverter.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxab.currencyconverter.BR
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "currency_table")
class CurrencyEntity:BaseObservable(),Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @IgnoredOnParcel
    @get:Bindable
    var currencyName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyName)
        }

    @IgnoredOnParcel
    @get:Bindable
    var currencySymbol: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencySymbol)
        }

    @IgnoredOnParcel
    @get:Bindable
    var currencyCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyCode)
        }

    @IgnoredOnParcel
    @get:Bindable
    var currencyRate: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyRate)
        }

}