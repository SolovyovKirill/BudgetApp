package io.teachmeskills.presentation.view.currencyselection


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.ItemCurrencyBinding
import io.teachmeskills.data.database.entity.Currency


class CurrencyAdapter(
    private val currencyList: MutableList<Currency>
): RecyclerView.Adapter<CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int = currencyList.size
}

class CurrencyViewHolder(
    private val binding: ItemCurrencyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Currency) {
        binding.tvCode.text = item.code
        binding.tvFlag.text = item.flag
        binding.tvTitle.text = item.title
    }
}

