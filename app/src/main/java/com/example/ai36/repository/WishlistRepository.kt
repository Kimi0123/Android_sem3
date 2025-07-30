package com.example.ai36.repository


import com.example.ai36.model.WishlistItemModel
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    suspend fun addToWishlist(item: WishlistItemModel)
    fun getWishlistItems(): Flow<List<WishlistItemModel>>
    suspend fun removeFromWishlist(item: WishlistItemModel)
}