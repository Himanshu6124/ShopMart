package com.himanshu.shopmart.domain

data class Order(
    val id: Int,
    val userId: Int,
    val items: List<OrderItem>,
    val totalAmount: Double,
    val status: String,
    val createdAt: String
)