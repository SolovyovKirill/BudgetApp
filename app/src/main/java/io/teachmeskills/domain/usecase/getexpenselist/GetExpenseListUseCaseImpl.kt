package io.teachmeskills.domain.usecase.getexpenselist

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.repository.ExpenseRepository
import io.teachmeskills.utils.Expenses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExpenseListUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : GetExpenseListUseCase{

    override suspend fun getExpenseList(data: String): Flow<List<ExpenseEntity>> =
        expenseRepository.getExpenseList(data)
}