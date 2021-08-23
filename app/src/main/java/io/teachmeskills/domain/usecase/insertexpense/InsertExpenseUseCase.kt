package io.teachmeskills.domain.usecase.insertexpense

import io.teachmeskills.data.database.entity.ExpenseEntity

interface InsertExpenseUseCase {

    suspend fun insertExpense(expenseEntity: ExpenseEntity)

}