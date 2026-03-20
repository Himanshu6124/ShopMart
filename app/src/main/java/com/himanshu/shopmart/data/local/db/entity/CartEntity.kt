package com.himanshu.shopmart.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val productId: Int,
    val quantity: Int,
    val isSynced: Boolean = false   // 🔥 important for offline sync
)