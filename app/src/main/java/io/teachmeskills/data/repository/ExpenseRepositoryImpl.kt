package io.teachmeskills.data.repository

import io.teachmeskills.data.database.dao.ExpenseDao
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override  fun getExpenseList(): Flow<List<ExpenseEntity>> =
        expenseDao.getExpenseList()

    override suspend fun insertExpense(expenseEntity: ExpenseEntity) {
        expenseDao.insertExpense(expenseEntity)
    }

    override suspend fun updateExpense(expenseEntity: ExpenseEntity) {
        expenseDao.updateExpense(expenseEntity)
    }

    override suspend fun deleteExpense(expenseEntity: ExpenseEntity) {
        expenseDao.deleteExpense(expenseEntity)
    }

    override suspend fun getExpenseList(data: String): Flow<List<ExpenseEntity>> =
        expenseDao.getExpenseList(data)

    override fun getExpenseById(id: Int): Flow<ExpenseEntity> =
        expenseDao.getExpenseById(id)

}