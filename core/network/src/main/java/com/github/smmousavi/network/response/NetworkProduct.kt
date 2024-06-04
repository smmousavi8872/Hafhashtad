package com.github.smmousavi.network.response

import com.google.gson.annotations.SerializedName


data class NetworkProduct(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("price")
    var price: Double? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("category")
    var category: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("rating")
    var rating: NetworkRating? = null,
)

data class NetworkRating(
    @SerializedName("rate")
    var rate: Double? = null,
    @SerializedName("count")
    var count: Int? = null,
)

