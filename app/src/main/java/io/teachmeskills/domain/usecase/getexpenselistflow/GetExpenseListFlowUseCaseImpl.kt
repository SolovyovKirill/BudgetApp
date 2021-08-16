package io.teachmeskills.domain.usecase.getexpenselistflow

import io.teachmeskills.data.database.entity.Expense
import io.teachmeskills.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExpenseListFlowUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : GetExpenseListFlowUseCase {
    override fun getExpenseList(): Flow<List<Expense>> = expenseRepository.getExpenseList().map { expense ->
        expense.map { expense ->
            Expense(expense.title, expense.currency, expense.amount, expense.tag, expense.date, expense.note)
        }
    }

}