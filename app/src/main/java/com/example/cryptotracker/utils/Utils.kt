package com.example.cryptotracker.utils

import com.example.cryptotracker.utils.Constants.imagesUrl
import java.math.RoundingMode
import java.text.DecimalFormat

class Utils {
    companion object {
        fun roundTrailingZeros(number: Double = 0.0): String {
            val decimalFormat = DecimalFormat("#.##")
            decimalFormat.roundingMode = RoundingMode.CEILING
            return decimalFormat.format(number).toString();
        }

        fun buildImageUrl(coinId: String): String {
            return "${imagesUrl}/${coinId}.png"
        }
    }
}