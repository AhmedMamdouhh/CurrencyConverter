package com.maxab.currencyconverter.manager.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maxab.currencyconverter.manager.connection.Resource
import com.maxab.currencyconverter.manager.utilities.Event

class ResponseManager{

    private val _observeResponseManager = MutableLiveData<Event<Resource<Any>>>()

    fun loading() { setResponseObject(Resource.Loading()) }
    fun hideLoading() { setResponseObject(Resource.HideLoading()) }
    fun success(message: String) { setResponseObject(Resource.Success(message)) }
    fun failed(message: String) { setResponseObject(Resource.Failed(message)) }
    fun noConnection() { setResponseObject(Resource.NoConnection()) }


    private fun setResponseObject(message: Resource<Any>) {
        _observeResponseManager.value = Event(message)
    }


    //getters
    val observeResponseManager: LiveData<Event<Resource<Any>>>
        get() = _observeResponseManager

}