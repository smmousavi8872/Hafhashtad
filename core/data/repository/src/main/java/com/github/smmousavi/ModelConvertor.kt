package com.github.smmousavi

import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.database.entity.RatingEntity
import com.github.smmousavi.model.Product
import com.github.smmousavi.model.Rating
import com.github.smmousavi.network.model.NetworkProduct
import com.github.smmousavi.network.model.NetworkRating

fun NetworkProduct.asEntity() = ProductEntity(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating.asEntity()
)

private fun NetworkRating.asEntity() = RatingEntity(
    rate = rate,
    count = count
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