package com.github.smmousavi

import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.database.entity.RatingEntity
import com.github.smmousavi.model.Product
import com.github.smmousavi.model.Rating
import com.github.smmousavi.network.response.NetworkProduct
import com.github.smmousavi.network.response.NetworkRating

fun NetworkProduct.asEntity() = ProductEntity(
    id = id ?: 0,
    title = title ?: "",
    price = price ?: 0.0,
    description = description ?: "",
    category = category ?: "",
    image = image ?: "",
    rating = rating?.asEntity() ?: RatingEntity(0.0, 0)
)

private fun NetworkRating.asEntity() = RatingEntity(
    rate = rate ?: 0.0,
    count = count ?: 0
)

fun ProductEntity.asExternalModel() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating.asExternalModel()
)

private fun RatingEntity.asExternalModel() = Rating(
    rate = rate,
    count = count
)