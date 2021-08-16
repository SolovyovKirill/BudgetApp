package io.teachmeskills.domain.usecase.deleteexpense

import io.teachmeskills.data.database.entity.Expense

interface DeleteExpenseUseCase {

    suspend fun deleteExpense(expense: Expense)

}