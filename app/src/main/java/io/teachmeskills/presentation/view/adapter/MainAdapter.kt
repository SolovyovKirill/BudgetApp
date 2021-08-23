package io.teachmeskills.presentation.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.teachmeskills.an03onl_accountingoffinancesapp.R

class MainAdapter : ListAdapter<MainItemModel, MainItemHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            SUMMARY_ITEM_TYPE -> SummaryItemHolder(itemView)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: MainItemHolder, position: Int) {
        val itemModel = getItem(position)
        when {
            (holder is SummaryItemHolder && itemModel is SummaryItemModel) -> {
                holder.bind(itemModel)
            }
        }
    }

    override fun onViewRecycled(holder: MainItemHolder) {
        super.onViewRecycled(holder)
        when (holder) {
            is SummaryItemHolder -> holder.recycle()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SummaryItemModel -> SUMMARY_ITEM_TYPE
            else -> super.getItemViewType(position)
        }
    }

    private class DiffCallBack : DiffUtil.ItemCallback<MainItemModel>() {

        override fun areItemsTheSame(oldItem: MainItemModel, newItem: MainItemModel): Boolean {
            return when {
                (oldItem is SummaryItemModel && newItem is SummaryItemModel) -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: MainItemModel, newItem: MainItemModel): Boolean {
            return when {
                (oldItem is SummaryItemModel && newItem is SummaryItemModel) ->
                    areContentsOfSummaryItemModelsTheSame(oldItem, newItem)
                else -> false
            }
        }

        private fun areContentsOfSummaryItemModelsTheSame(
            oldItem: SummaryItemModel,
            newItem: SummaryItemModel
        ): Boolean {
            return oldItem.currencySummaries == newItem.currencySummaries
                    && oldItem.dateRange == newItem.dateRange
        }
    }

    companion object {

        private const val SUMMARY_ITEM_TYPE = R.layout.item_summary

    }
}