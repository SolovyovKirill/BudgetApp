package io.teachmeskills.domain.usecase.updateexpense

import io.teachmeskills.data.database.entity.Expense
import io.teachmeskills.domain.repository.ExpenseRepository

class UpdateExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
    ) : UpdateExpenseUseCase {
    override suspend fun updateExpense(expense: Expense) {
        expenseRepository.updateExpense(expense)
    }
}