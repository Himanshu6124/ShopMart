package com.himanshu.shopmart.data.remote.dto

data class CartDto(
    val id: Int,
    val userId: Int,
    val productId: Int,
    val quantity: Int
)