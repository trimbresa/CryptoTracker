package com.example.cryptotracker.models

class Usd (
    val price: Double,
    val volume_24h: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_7d: Double,
)

class Quote (
    val USD: Usd
)

class CryptoCoinModel (
    val id: String,
    val name: String,
    val symbol: String,
    val slug: String,
    val num_market_pairs: Int,
    val date_added: String,
    val quote: Quote
)