package io.teachmeskills.data.api

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.ItemCurrencyBinding

class CurrencyAdapter(val listCurrency : List<Currency>) : ListAdapter<Currency, CurrencyViewHolder>(DiffUtilCallback()), Filterable {

    private var mCurrency: List<Currency> = listCurrency

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(
            ItemCurrencyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getFilter(): Filter {
        return object  : Filter() {

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mCurrency = results?.values as List<Currency>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint?.toString()

                val filterResult = Filter.FilterResults()
                filterResult.values = if (queryString == null || queryString.isNotEmpty())
                    listCurrency
                else
                    listCurrency.filter {
                        it.name.toLowerCase().contains(queryString)
                        it.symbol.toLowerCase().contains(queryString)
                    }
                return  filterResult
            }



        }
    }
}

class CurrencyViewHolder(
    private val binding: ItemCurrencyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Currency) {
        binding.tvSymbol.text = item.symbol
        binding.tvName.text = item.name
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.name == newItem.name && oldItem.symbol == newItem.symbol
    }
}
