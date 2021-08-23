package io.teachmeskills.domain.usecase.updateexpense

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.repository.ExpenseRepository

class UpdateExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
    ) : UpdateExpenseUseCase {
    override suspend fun updateExpense(expenseEntity: ExpenseEntity) {
        expenseRepository.updateExpense(expenseEntity)
    }
}