package io.teachmeskills.domain.usecase.insertexpense

import io.teachmeskills.data.database.entity.Expense

interface InsertExpenseUseCase {

    suspend fun insertExpense(expense: Expense)

}