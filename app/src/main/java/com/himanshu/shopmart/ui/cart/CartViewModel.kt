package com.himanshu.shopmart.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.shopmart.data.local.db.entity.CartEntity
import com.himanshu.shopmart.domain.CartItem
import com.himanshu.shopmart.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartState(
    val items: List<CartItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState(isLoading = true))
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()

    // Assuming we use a fixed user ID for now since we don't have auth context
    private val currentUserId = 1

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        cartRepository.getCartItems(currentUserId)
            .onEach { items ->
                val total = items.sumOf { it.product.price * it.quantity }
                _cartState.value = CartState(
                    items = items,
                    totalAmount = total,
                    isLoading = false
                )
            }
            .catch { e ->
                _cartState.value = _cartState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage
                )
            }
            .launchIn(viewModelScope)
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity <= 0) {
                cartRepository.removeFromCart(cartItem.id)
            } else {
                cartRepository.updateQuantity(cartItem.id, newQuantity)
            }
        }
    }

    fun addToCart(productId: Int) {
        viewModelScope.launch {
            // Check if product is already in cart to update quantity or add new
            val currentItems = _cartState.value.items
            val existingItem = currentItems.find { it.product.id == productId }

            if (existingItem != null) {
                cartRepository.updateQuantity(existingItem.id, existingItem.quantity + 1)
            } else {
                // Generate a pseudo-random unique ID for local cart entity since it's an Int.
                // In a real app, Room would auto-generate this if set to autoGenerate=true, 
                // but CartEntity doesn't have autoGenerate=true on PrimaryKey.
                // Assuming max id + 1 or a timestamp based mechanism. using simple random for now.
                val newId = (0..1000000).random() 
                val newCartEntity = CartEntity(
                    id = newId,
                    userId = currentUserId,
                    productId = productId,
                    quantity = 1,
                    isSynced = false
                )
                cartRepository.addToCart(newCartEntity)
            }
        }
    }
}
