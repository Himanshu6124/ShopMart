package com.himanshu.shopmart.domain.repository

import com.himanshu.shopmart.data.local.db.entity.ProductEntity
import com.himanshu.shopmart.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun refreshProducts()
}
