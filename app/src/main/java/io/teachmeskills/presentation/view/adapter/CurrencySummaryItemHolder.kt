package io.teachmeskills.presentation.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_currency_summary.view.*

class CurrencySummaryItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(itemModel : CurrencySummaryItemModel) {
        itemView.text_amount.text = itemModel.amountText
        itemView.text_currency.text = itemModel.currencyText
    }

    fun recycle() {
        itemView.text_amount.text = ""
        itemView.text_currency.text = ""
    }
}