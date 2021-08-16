package io.teachmeskills.domain.usecase.updateexpense

import io.teachmeskills.data.database.entity.Expense

interface UpdateExpenseUseCase {

    suspend fun updateExpense(expense: Expense)

}