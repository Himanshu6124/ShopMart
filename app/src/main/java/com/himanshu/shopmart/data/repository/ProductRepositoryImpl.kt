package com.himanshu.shopmart.data.repository

import com.himanshu.shopmart.data.remote.ApiService
import com.himanshu.shopmart.domain.Product
import com.himanshu.shopmart.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProducts(): Flow<Result<List<Product>>> = flow {
        try {
            val response = apiService.getProducts()
            val products = response.map { dto ->
                Product(
                    id = dto.id,
                    title = dto.title,
                    price = dto.price,
                    description = dto.description,
                    category = dto.category,
                    image = dto.image,
                    rating = dto.rating,
                    stock = dto.stock
                )
            }
            emit(Result.success(products))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
