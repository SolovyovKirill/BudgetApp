package io.teachmeskills.presentation.view.adapter

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import kotlinx.android.synthetic.main.item_summary.view.*

class SummaryItemHolder(itemView : View) : MainItemHolder(itemView) {

    private var adapter = SummaryAdapter()
    private val layoutManager = LinearLayoutManager(itemView.context,
    LinearLayoutManager.HORIZONTAL, false)

    private val snapHelper = PagerSnapHelper()

    init {
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        itemView.recycler_view_amount_and_currency.adapter = adapter
        itemView.recycler_view_amount_and_currency.layoutManager = layoutManager
        itemView.recycler_view_amount_and_currency.itemAnimator = null
        snapHelper.attachToRecyclerView(itemView.recycler_view_amount_and_currency)
    }

     fun bind(model : SummaryItemModel) {
        itemView.chip_date_range.text = model.dateRangeText
        itemView.chip_date_range.setOnCloseIconClickListener { showPopupMenu(model) }
        itemView.rv_total_expense.isVisible = model.itemModels.isEmpty()
        adapter.submitList(model.itemModels)
    }

    private fun showPopupMenu(model: SummaryItemModel) {
        val popupMenu = PopupMenu(itemView.context, itemView.chip_date_range)
        popupMenu.inflate(R.menu.menu_date_range)
        popupMenu.setOnMenuItemClickListener { menuItemSelected(it, model) }
        popupMenu.show()
    }

    private fun menuItemSelected(item: MenuItem, model: SummaryItemModel): Boolean {
        return when (item.itemId) {
            R.id.today -> { model.onTodayClick(); true }
            R.id.this_week -> { model.onThisWeekClick(); true }
            R.id.this_month -> { model.onThisMonthClick(); true }
            R.id.all_time -> { model.onAllTimeClick(); true }
            else -> false
        }
    }

    fun recycle() {
        itemView.chip_date_range.text =""
        itemView.chip_date_range.setOnClickListener(null)
        itemView.rv_total_expense.isVisible = false
        adapter.submitList(emptyList())
    }

}