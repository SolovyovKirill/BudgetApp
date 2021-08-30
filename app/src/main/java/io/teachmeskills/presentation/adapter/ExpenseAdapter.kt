package io.teachmeskills.presentation.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.ItemExpenseBinding
import io.teachmeskills.data.database.entity.ExpenseEntity


class ExpenseAdapter(
//    private var expenses: MutableList<ExpenseEntity>,
    private val onItemClickListener: OnItemClickListener
) :
//    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder =
//        ExpenseViewHolder(
//            ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )
//
//    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
//        holder.bind(expenses[position])
//        holder.itemView.setOnClickListener {
//            onItemClickListener.onClick(getItem(position))
//        }
//    }
//
//    fun update(newExpenses: List<ExpenseEntity>) {
//        expenses.clear()
//        expenses.addAll(newExpenses)
//        notifyDataSetChanged()
//    }
//
//    class ExpenseViewHolder(
//        private val binding: ItemExpenseBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(expense: ExpenseEntity) {
//            binding.tvTitle.text = expense.title
//            binding.tvTag.text = expense.tag
//            binding.tvCurrency.text = expense.currency
//            binding.tvAmount.text = expense.amount.toString()
//            binding.tvDate.text = expense.date
//        }
//    }
//
//    override fun getItemCount(): Int = expenses.size
//
//}


    ListAdapter<ExpenseEntity, ExpenseAdapter.ExpenseViewHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder =
        ExpenseViewHolder(
            ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(getItem(position))
        }
    }

    class ExpenseViewHolder(
        private val binding: ItemExpenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExpenseEntity) {
            binding.tvTitle.text = item.title
            binding.tvTag.text = item.tag
            binding.tvCurrency.text = item.currency
            binding.tvAmount.text = item.amount.toString()
            binding.tvDate.text = item.date
        }
    }

}

class DiffUtilItemCallback : DiffUtil.ItemCallback<ExpenseEntity>() {
    override fun areItemsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
        return oldItem.amount == newItem.amount
                && oldItem.tag == newItem.tag
                && oldItem.currency == newItem.currency
                && oldItem.title == newItem.title
                && oldItem.date == newItem.date
    }
}