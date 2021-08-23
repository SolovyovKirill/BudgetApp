package io.teachmeskills.domain.usecase.deleteexpense

import io.teachmeskills.data.database.entity.ExpenseEntity

interface DeleteExpenseUseCase {

    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

}