package io.teachmeskills.domain.usecase.deleteexpense

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.repository.ExpenseRepository

class DeleteExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : DeleteExpenseUseCase {
    override suspend fun deleteExpense(expenseEntity: ExpenseEntity) {
        expenseRepository.deleteExpense(expenseEntity)
    }
}