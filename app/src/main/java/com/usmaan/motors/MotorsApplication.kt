package com.usmaan.motors

import android.app.Application
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MotorsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MotorsApplication)
            modules(MotorsModule.modules)
        }
    }
}

object MotorsModule {
    val modules = module {
        single { provideOkHttpClient() }
        single { SearchRepository(get()) }
        single { SearchViewModel(get()) }
        single { provideSearchService(get()) }
        single { provideRetrofit(get()) }
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://motors-uk-ruby-api.herokuapp.com/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().callTimeout(1, TimeUnit.MINUTES).build()

    private fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

}