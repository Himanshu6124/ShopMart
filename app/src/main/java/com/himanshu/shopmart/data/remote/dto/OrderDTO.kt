package com.himanshu.shopmart.data.remote.dto

data class OrderDto(
    val id: Int,
    val userId: Int,
    val items: List<OrderItemDto>,
    val totalAmount: Double,
    val status: String,
    val createdAt: String
)