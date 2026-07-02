package com.mahdi.assignment.shoppingapp.core.di

import com.mahdi.assignment.shoppingapp.core.common.DefaultDispatcherProvider
import com.mahdi.assignment.shoppingapp.core.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}
