package com.himanshu.shopmart.data.local.mapper

import com.himanshu.shopmart.data.local.db.entity.ProductEntity
import com.himanshu.shopmart.data.remote.dto.ProductDto
import com.himanshu.shopmart.domain.Product

fun ProductDto.toEntity() = ProductEntity(
    id, title, price, description, category, image, rating, stock
)

fun ProductEntity.toDomain() = Product(
    id, title, price, description, category, image, rating, stock
)