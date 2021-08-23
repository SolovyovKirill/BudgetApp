package io.teachmeskills.presentation.view.adapter

data class CurrencySummaryItemModel(
    val currency: String,
    val amount: Double
) {
    val amountText by lazy { "%.2f".format(amount) }
    val currencyText by lazy { "(${currency}" }
}