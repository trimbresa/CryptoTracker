package com.example.cryptotracker.models

class Usd (
    val price: Double,
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
    val quote: Quote,
    var isFavorited : Boolean
)