package com.himanshu.shopmart.domain

data class CartItem(
    val id: Int,
    val product: Product,
    val quantity: Int
)