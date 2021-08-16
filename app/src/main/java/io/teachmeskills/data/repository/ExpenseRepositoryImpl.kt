package io.teachmeskills.data.repository

import io.teachmeskills.data.database.dao.ExpenseDao
import io.teachmeskills.data.database.entity.Expense
import io.teachmeskills.domain.repository.ExpenseRepository
import io.teachmeskills.utils.Expenses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override  fun getExpenseList(): Flow<List<Expense>> =
        expenseDao.getExpenseList()

    override suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    override suspend fun getExpenseList(date: String): List<Expense> =
        expenseDao.getExpenseList(date)

//    override suspend fun getExpenseById(expenseId: Int): Expense?
//    = expenseDao.getExpenseById(expenseId)

}