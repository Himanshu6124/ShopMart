package com.himanshu.shopmart.data.remote

import com.himanshu.shopmart.data.remote.dto.ProductDto
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
}