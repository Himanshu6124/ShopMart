package com.himanshu.shopmart.data.repository

import com.himanshu.shopmart.data.local.db.dao.CartDao
import com.himanshu.shopmart.data.local.db.entity.CartEntity
import com.himanshu.shopmart.data.local.mapper.toDomain
import com.himanshu.shopmart.domain.CartItem
import com.himanshu.shopmart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(userId: Int): Flow<List<CartItem>> {
        return cartDao.getCartItems(userId).map { list ->
            list.map { cartWithProduct ->
                cartWithProduct.cart.toDomain(cartWithProduct.product.toDomain())
            }
        }
    }

    override suspend fun addToCart(cartEntity: CartEntity) {
        cartDao.addToCart(cartEntity)
    }

    override suspend fun updateQuantity(id: Int, quantity: Int) {
        cartDao.updateQuantity(id, quantity)
    }

    override suspend fun removeFromCart(id: Int) {
        cartDao.removeFromCart(id)
    }

    override suspend fun getUnsyncedItems(): List<CartEntity> {
        return cartDao.getUnsyncedItems()
    }

    override suspend fun markSynced(id: Int) {
        cartDao.markSynced(id)
    }
}
