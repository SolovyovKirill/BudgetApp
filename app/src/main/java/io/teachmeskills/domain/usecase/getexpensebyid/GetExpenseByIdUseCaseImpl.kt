package io.teachmeskills.domain.usecase.getexpensebyid

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpenseByIdUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : GetExpenseByIdUseCase {
    override fun getExpenseById(id: Int) : Flow<ExpenseEntity> =
        expenseRepository.getExpenseById(id)
}