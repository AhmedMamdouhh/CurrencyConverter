package com.maxab.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.maxab.currencyconverter.dp.CurrencyDataBase
import com.maxab.currencyconverter.manager.base.ResponseManager
import com.maxab.currencyconverter.manager.connection.Api
import com.maxab.currencyconverter.manager.connection.ApiEndPoints
import com.maxab.currencyconverter.manager.utilities.Constants
import com.maxab.currencyconverter.model.CurrencyEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Retrofit configuration :
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor)
            : OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ApiEndPoints.BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Singleton
    @Provides
    fun provideResponseManager() = ResponseManager()

    @Provides
    fun provideCurrencyEntity() = CurrencyEntity()



    //Room configuration :
    @Singleton
    @Provides
    fun provideCurrencyDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CurrencyDataBase::class.java,
        Constants.CURRENCY_DATA_BASE_NAME
    ).allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideNCurrencyDao(currencyDataBase: CurrencyDataBase) = currencyDataBase.getCurrencyDao()


}