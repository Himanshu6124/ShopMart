package com.himanshu.shopmart.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.shopmart.domain.Product
import com.himanshu.shopmart.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

data class ProductState(
    val isLoading: Boolean = false,
    val data: List<Product> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

    init {
        observeProducts()
        refreshProducts()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collect { products ->
                _state.update {
                    it.copy(data = products)
                }
            }
        }
    }
    private fun refreshProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                productRepository.refreshProducts()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }
}
