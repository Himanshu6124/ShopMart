package com.himanshu.shopmart.di

import com.himanshu.shopmart.data.repository.CartRepositoryImpl
import com.himanshu.shopmart.data.repository.ProductRepositoryImpl
import com.himanshu.shopmart.domain.repository.CartRepository
import com.himanshu.shopmart.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository
}
