package com.example.multisearch.data.model

data class Offer (
    val id: Int = 0,
    val startDate: Long = 0,
    val endDate : Long = 0,
    val idProduct: Int = 0,
    val idSite: Int = 0,
    val product: Product = Product(),
    val price: Number,
    val icon : String,
    val link : String,
    val site: Site = Site()
)