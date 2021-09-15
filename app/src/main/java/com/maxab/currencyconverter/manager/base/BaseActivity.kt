package com.maxab.currencyconverter.manager.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.maxab.currencyconverter.R
import com.maxab.currencyconverter.manager.utilities.Constants
import com.maxab.currencyconverter.manager.utilities.EventObserver
import com.maxab.currencyconverter.ui.response.error.ErrorSheet
import com.maxab.currencyconverter.ui.response.no_connection.NoConnectionSheet
import com.maxab.currencyconverter.ui.response.success.SuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private var loadingBar: Dialog? = null
    private val baseViewModel: BaseViewModel by viewModels()
    @Inject
    lateinit var responseManager: ResponseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeProgress()
        observeResponse()
        observeLoading()
        observeSuccess()
        observeFailed()
        observeNoConnection()
        observeHideLoading()
    }

    private fun observeHideLoading() {
        baseViewModel.observeHideLoading.observe(this,EventObserver{
            hideProgress()
        })
    }

    private fun observeNoConnection() {
        baseViewModel.observeNoConnection.observe(this,EventObserver{
            hideProgress()
            noConnection()
        })
    }

    private fun observeFailed() {
        baseViewModel.observeFailed.observe(this,EventObserver{ errorMessage->
            hideProgress()
            failedMessage(errorMessage)
        })
    }

    private fun observeSuccess() {
        baseViewModel.observeSuccess.observe(this,EventObserver{successMessage->
            hideProgress()
            successMessage(successMessage)
        })
    }

    private fun observeLoading() {
        baseViewModel.observeLoading.observe(this,EventObserver{
            showProgress()
        })
    }


    open fun observeResponse() {
        responseManager.observeResponseManager.observe(this, EventObserver
        { responseResource ->
            try {
                baseViewModel.getResponseState(responseResource)
            } catch (ex: NullPointerException) {
            }
        })
    }

    //Snack bar :
    private fun successMessage(message: String?) {
        val successSheet = SuccessDialog()
        val bundle = Bundle()
        bundle.putString(Constants.MESSAGE, message)
        successSheet.arguments = bundle

        successSheet.show(supportFragmentManager, Constants.SUCCESS_SHEET)
    }

    private fun failedMessage(message: String?) {
        val errorSheet = ErrorSheet()
        val bundle = Bundle()
        bundle.putString(Constants.MESSAGE, message)
        errorSheet.arguments = bundle

        errorSheet.show(supportFragmentManager, Constants.ERROR_SHEET)
    }

    private fun noConnection() {
        NoConnectionSheet().show(supportFragmentManager, Constants.NO_CONNECTION_SHEET)
    }

    @SuppressLint("InflateParams")
    private fun initializeProgress() {
        loadingBar = Dialog(this, R.style.Theme_AppCompat_DayNight)
        loadingBar!!.setCancelable(false)
        loadingBar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = LayoutInflater.from(this)
        val loadingView = inflater.inflate(R.layout.layout_loader, null)
        loadingBar!!.setContentView(loadingView)
        loadingBar!!.window!!.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#0effffff"))
        )
    }


    private fun showProgress() {
        if (loadingBar != null && !this.isFinishing) loadingBar!!.show()
    }

    private fun hideProgress() {
        if (loadingBar != null && loadingBar!!.isShowing && !this.isFinishing) loadingBar!!.dismiss()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}