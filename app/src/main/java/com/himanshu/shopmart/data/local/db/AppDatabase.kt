package com.himanshu.shopmart.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.himanshu.shopmart.data.local.db.dao.CartDao
import com.himanshu.shopmart.data.local.db.dao.OrderDao
import com.himanshu.shopmart.data.local.db.dao.ProductDao
import com.himanshu.shopmart.data.local.db.entity.CartEntity
import com.himanshu.shopmart.data.local.db.entity.OrderEntity
import com.himanshu.shopmart.data.local.db.entity.OrderItemEntity
import com.himanshu.shopmart.data.local.db.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        CartEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}