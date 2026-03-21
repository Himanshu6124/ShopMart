package com.himanshu.shopmart.domain.repository

import com.himanshu.shopmart.data.local.db.entity.CartEntity
import com.himanshu.shopmart.domain.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(userId: Int): Flow<List<CartItem>>
    suspend fun addToCart(cartEntity: CartEntity)
    suspend fun updateQuantity(id: Int, quantity: Int)
    suspend fun removeFromCart(id: Int)
    suspend fun getUnsyncedItems(): List<CartEntity>
    suspend fun markSynced(id: Int)
}
