package io.teachmeskills.presentation.view.adapter

import android.content.Context
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.utils.DateRange

class SummaryItemModel(
    context: Context,
    val currencySummaries: List<Pair<String, Double>>,
    val dateRange: DateRange
) : MainItemModel {

    var itemModels: List<CurrencySummaryItemModel>
    var dateRangeText: String
    var dateRangeChange: ((DateRange) -> Unit)? = null

    init {
        itemModels = createItemModels(currencySummaries)
        dateRangeText = createDateRangeText(context, dateRange)

    }

    private fun createItemModels(currencySummaries: List<Pair<String, Double>>) =
        currencySummaries.map { createCurrencySummaryItemModel(it.first, it.second) }

    private fun createCurrencySummaryItemModel(currency: String, amount: Double) =
        CurrencySummaryItemModel(currency, amount)

    private fun createDateRangeText(context: Context, dateRange: DateRange): String {
        return when (dateRange) {
            DateRange.TODAY -> context.getString(R.string.today)
            DateRange.THIS_WEEK -> context.getString(R.string.this_week)
            DateRange.THIS_MONTH -> context.getString(R.string.this_month)
            DateRange.ALL_TIME -> context.getString(R.string.all_time)

        }
    }

    fun onTodayClick() = dateRangeChange?.invoke(DateRange.TODAY)

    fun onThisWeekClick() = dateRangeChange?.invoke(DateRange.THIS_WEEK)

    fun onThisMonthClick() = dateRangeChange?.invoke(DateRange.THIS_MONTH)

    fun onAllTimeClick() = dateRangeChange?.invoke(DateRange.ALL_TIME)


}

