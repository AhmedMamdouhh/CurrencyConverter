package com.maxab.currencyconverter.di

import com.maxab.currencyconverter.manager.ResponseManager
import com.maxab.currencyconverter.manager.connection.Api
import com.maxab.currencyconverter.manager.connection.ApiEndPoints
import com.maxab.currencyconverter.manager.connection.Resource
import com.maxab.currencyconverter.model.CurrencyEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideResponseManager()= ResponseManager()

    @Singleton
    @Provides
    fun provideCurrencyEntity()=CurrencyEntity()


//    //Room configuration :
//    @Singleton
//    @Provides
//    fun provideNewsDatabase(
//        @ApplicationContext context: Context
//    ) = Room.databaseBuilder(
//        context,
//        NewsDataBase::class.java,
//        SyncStateContract.Constants.NEWS_DATABASE_NAME
//    )
//
//    @Singleton
//    @Provides
//    fun provideNewsDao(newsDataBase: NewsDataBase) = newsDataBase.getNewsDao()

}