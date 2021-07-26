package com.example.cryptotracker

import android.app.Application
import com.example.cryptotracker.models.CryptoCoinModel

 class CryptoApp: Application() {
    var cryptoCoinModels: List<CryptoCoinModel> = emptyList()
        get() = field
        set(value) {
            field = value
        }
}