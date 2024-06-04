package com.github.smmousavi.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.database.entity.RatingEntity

@Dao
interface ProductDao {

    // update or insert a products under the primary key
    @Upsert
    suspend fun upsertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity

    @Query(value = "DELETE FROM products WHERE id in (:ids)")
    suspend fun deleteProducts(ids: List<Int>)


    @Upsert
    suspend fun upsertRating(rating: RatingEntity)

    @Query("SELECT * From ratings WHERE id = :id")
    suspend fun getRatingById(id: Int): RatingEntity
}