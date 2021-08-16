package io.teachmeskills.domain.usecase.insertexpense

import io.teachmeskills.data.database.entity.Expense
import io.teachmeskills.domain.repository.ExpenseRepository

class InsertExpenseUseCaseImpl (
    private val expenseRepository: ExpenseRepository
        ) : InsertExpenseUseCase {
    override suspend fun insertExpense(expense: Expense) {
        expenseRepository.insertExpense(expense)
    }
}