package io.teachmeskills.presentation.view.main

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.utils.Expenses

interface OnItemClickListener {

    fun onClick(expenseEntity: ExpenseEntity)

}
