package io.teachmeskills.domain.usecase.getexpenselist1

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpenseListUseCase1Impl(
    private val expenseRepository: ExpenseRepository
) : GetExpenseListUseCase1 {
    override suspend fun getExpenseList(): Flow<List<ExpenseEntity>> =
        expenseRepository.getExpenseList()
}