package com.himanshu.shopmart.data.local.db.dao

import androidx.room.*
import com.himanshu.shopmart.data.local.db.entity.CartEntity
import com.himanshu.shopmart.data.local.db.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

data class CartWithProduct(
    @Embedded val cart: CartEntity,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity
)

@Dao
interface CartDao {

    @Transaction
    @Query("SELECT * FROM cart WHERE userId = :userId")
    fun getCartItems(userId: Int): Flow<List<CartWithProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cart: CartEntity)

    @Query("UPDATE cart SET quantity = :quantity, isSynced = 0 WHERE id = :id")
    suspend fun updateQuantity(id: Int, quantity: Int)

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun removeFromCart(id: Int)

    @Query("SELECT * FROM cart WHERE isSynced = 0")
    suspend fun getUnsyncedItems(): List<CartEntity>

    @Query("UPDATE cart SET isSynced = 1 WHERE id = :id")
    suspend fun markSynced(id: Int)
}