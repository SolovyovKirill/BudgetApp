package io.teachmeskills.presentation.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.ItemCurrencySummaryBinding
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.ItemExpenseBinding
import io.teachmeskills.data.database.entity.ExpenseEntity

class TotalExpenseAdapter() : ListAdapter<ExpenseSum, TotalExpenseAdapter.TotalExpenseViewHolder>(DiffUtilItemCallbackTotal()) {

    class TotalExpenseViewHolder(private val binding: ItemCurrencySummaryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : ExpenseSum) {
            binding.textCurrency.text = item.currency
            binding.textAmount.text = item.amount.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalExpenseViewHolder =
        TotalExpenseViewHolder(
            ItemCurrencySummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TotalExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DiffUtilItemCallbackTotal : DiffUtil.ItemCallback<ExpenseSum>() {
    override fun areItemsTheSame(oldItem: ExpenseSum, newItem: ExpenseSum): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExpenseSum, newItem: ExpenseSum): Boolean {
        return oldItem.amount == newItem.amount && oldItem.currency == newItem.currency

    }

}