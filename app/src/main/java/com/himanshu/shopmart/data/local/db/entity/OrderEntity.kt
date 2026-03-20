package com.himanshu.shopmart.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val totalAmount: Double,
    val status: String,
    val createdAt: String,
    val isSynced: Boolean = false
)