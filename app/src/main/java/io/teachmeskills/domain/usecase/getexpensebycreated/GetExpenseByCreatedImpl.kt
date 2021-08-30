package io.teachmeskills.domain.usecase.getexpensebycreated

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpenseByCreatedImpl(
    private val expenseRepository: ExpenseRepository
) : GetExpenseByCreated {
    override suspend fun getExpenseByCreated(date1: Long, date2: Long) : List<ExpenseEntity> =
        expenseRepository.getExpenseByCreated(date1, date2)
}