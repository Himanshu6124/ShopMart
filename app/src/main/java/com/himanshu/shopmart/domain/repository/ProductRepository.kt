package com.himanshu.shopmart.domain.repository

import com.himanshu.shopmart.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Result<List<Product>>>
}
