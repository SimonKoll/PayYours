package at.htl.leonding.payyours

import java.io.Serializable
import java.text.DecimalFormat
import java.util.Locale.getDefault

data class Payment(var courts: Int, var players: Int, var pricePerUnitText: String) : Serializable {
    val amount: Double
        get() {
            val decimalFormat = DecimalFormat.getInstance(getDefault()) as DecimalFormat
            val pricePerUnit = decimalFormat.parse(pricePerUnitText) ?: return -1.0
            return courts * pricePerUnit.toDouble() / players


        }
}