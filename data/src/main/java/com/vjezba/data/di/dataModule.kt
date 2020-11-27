package com.vjezba.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vjezba.data.NewsRepositoryImpl
import com.vjezba.data.environments.Constants
import com.vjezba.data.service.NewsService
import com.vjezba.domain.NewsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


//it creates a module in Koin which would be used by Koin to provide all the dependencies.
val dataModule = module {

    //it creates a singleton that can be used across the app as a singular instance.
    single {
        val callAdapterFactory: CallAdapter.Factory = CoroutineCallAdapterFactory()
        callAdapterFactory
    }

    single { GsonConverterFactory.create() as Converter.Factory }
    single { HttpLoggingInterceptor.Level.BODY }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor())
                .callTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
        }.build()
    }
    single {
         Retrofit.Builder()
            .baseUrl( Constants.RETROFIT_BASE_URL)
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
    }
    single { get<Retrofit>().create(NewsService::class.java) }

    factory {
        val newsRepository: NewsRepository =
            NewsRepositoryImpl(get(), get(), get())
        newsRepository
    }

}