package com.himanshu.shopmart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import androidx.room.Room
import com.himanshu.shopmart.data.local.db.AppDatabase
import com.himanshu.shopmart.data.local.db.dao.CartDao
import com.himanshu.shopmart.data.local.db.dao.OrderDao
import com.himanshu.shopmart.data.local.db.dao.ProductDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shopmart_db"
        ).build()
    }

    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao {
        return db.productDao()
    }

    @Provides
    fun provideCartDao(db: AppDatabase): CartDao {
        return db.cartDao()
    }

    @Provides
    fun provideOrderDao(db: AppDatabase): OrderDao {
        return db.orderDao()
    }
}