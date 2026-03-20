package com.himanshu.shopmart.data.local.mapper

import com.himanshu.shopmart.data.local.db.entity.CartEntity
import com.himanshu.shopmart.domain.CartItem
import com.himanshu.shopmart.domain.Product

fun CartEntity.toDomain(product: Product) = CartItem(
    id = id,
    product = product,
    quantity = quantity
)