package com.himanshu.shopmart.data.repository

import com.himanshu.shopmart.data.local.db.dao.ProductDao
import com.himanshu.shopmart.data.local.db.entity.ProductEntity
import com.himanshu.shopmart.data.local.mapper.toDomain
import com.himanshu.shopmart.data.local.mapper.toEntity
import com.himanshu.shopmart.data.remote.ApiService
import com.himanshu.shopmart.domain.Product
import com.himanshu.shopmart.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> {
        return dao.getAllProducts()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun refreshProducts() {
        val products = apiService.getProducts()
        dao.insertAll(products.map { it.toEntity() })
    }
}
