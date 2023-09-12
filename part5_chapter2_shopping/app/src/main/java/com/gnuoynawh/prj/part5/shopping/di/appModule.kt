package com.gnuoynawh.prj.part5.shopping.di

import com.gnuoynawh.prj.part5.shopping.data.network.buildOkHttpClient
import com.gnuoynawh.prj.part5.shopping.data.network.provideGsonConverterFactory
import com.gnuoynawh.prj.part5.shopping.data.network.provideProductApiService
import com.gnuoynawh.prj.part5.shopping.data.network.provideProductRetrofit
import com.gnuoynawh.prj.part5.shopping.data.repository.DefaultProductRepository
import com.gnuoynawh.prj.part5.shopping.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal val appModule = module {

    //
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    single<ProductRepository> { DefaultProductRepository(get(), get()) }

    // Retrofit
    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideProductRetrofit(get(), get()) }
    single { provideProductApiService(get()) }

}