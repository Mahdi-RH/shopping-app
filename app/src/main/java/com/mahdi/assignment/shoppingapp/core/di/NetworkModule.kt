package com.mahdi.assignment.shoppingapp.core.di

import com.mahdi.assignment.shoppingapp.core.network.ApiService
import com.mahdi.assignment.shoppingapp.core.network.RetrofitFactory
import com.mahdi.assignment.shoppingapp.feature.search.data.ProductRepositoryImpl
import com.mahdi.assignment.shoppingapp.feature.search.domain.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitFactory.createRetrofit()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(apiService: ApiService): ProductRepository =
        ProductRepositoryImpl(apiService)

}