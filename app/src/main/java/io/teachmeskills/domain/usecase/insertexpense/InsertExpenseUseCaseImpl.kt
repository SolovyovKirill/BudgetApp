package io.teachmeskills.domain.usecase.insertexpense

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.repository.ExpenseRepository

class InsertExpenseUseCaseImpl (
    private val expenseRepository: ExpenseRepository
        ) : InsertExpenseUseCase {
    override suspend fun insertExpense(expenseEntity: ExpenseEntity) {
        expenseRepository.insertExpense(expenseEntity)
    }
}