package com.example.cryptotracker

import android.app.Application
import com.example.cryptotracker.models.CryptoCoinModel

 class CryptoApp: Application() {
    var cryptoCoinModels: MutableList<CryptoCoinModel> = mutableListOf()
        get() = field
        set(value) {
            field = value
        }

     fun addItemToList(item: CryptoCoinModel) {
         cryptoCoinModels.add(item)
     }

     fun removeItemFromList(coin: CryptoCoinModel) {
         cryptoCoinModels.remove(coin)
     }
}