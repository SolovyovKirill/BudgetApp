package io.teachmeskills.domain.usecase.updateexpense

import io.teachmeskills.data.database.entity.ExpenseEntity

interface UpdateExpenseUseCase {

    suspend fun updateExpense(expenseEntity: ExpenseEntity)

}