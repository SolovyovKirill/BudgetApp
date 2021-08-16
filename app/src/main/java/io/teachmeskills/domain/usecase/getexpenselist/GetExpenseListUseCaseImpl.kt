package io.teachmeskills.domain.usecase.getexpenselist

import io.teachmeskills.domain.repository.ExpenseRepository
import io.teachmeskills.utils.Expenses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExpenseListUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : GetExpenseListUseCase{
    override suspend fun getExpenseList(date: String): List<Expenses> =
        expenseRepository.getExpenseList(date).map {
            Expenses(it.amount, it.title, it.tag, it.currency)
        }



}