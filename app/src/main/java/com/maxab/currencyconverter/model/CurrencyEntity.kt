package com.maxab.currencyconverter.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.maxab.currencyconverter.BR
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class CurrencyEntity:BaseObservable(),Parcelable {

    @IgnoredOnParcel
    @get:Bindable
    var currencyName: String = "European euro"
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyName)
        }

    @IgnoredOnParcel
    @get:Bindable
    var currencySymbol: String = "€"
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