package com.himanshu.shopmart.data.local.db.dao

import androidx.room.*
import com.himanshu.shopmart.data.local.db.entity.OrderEntity
import com.himanshu.shopmart.data.local.db.entity.OrderItemEntity
import kotlinx.coroutines.flow.Flow

data class OrderWithItems(
    @Embedded val order: OrderEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItemEntity>
)

@Dao
interface OrderDao {

    @Transaction
    @Query("SELECT * FROM orders WHERE userId = :userId")
    fun getOrders(userId: Int): Flow<List<OrderWithItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Query("SELECT * FROM orders WHERE isSynced = 0")
    suspend fun getUnsyncedOrders(): List<OrderEntity>

    @Query("UPDATE orders SET isSynced = 1 WHERE id = :id")
    suspend fun markOrderSynced(id: Int)
}