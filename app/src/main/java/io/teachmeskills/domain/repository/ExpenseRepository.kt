package io.teachmeskills.domain.repository


import io.teachmeskills.data.database.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow


interface ExpenseRepository {

    fun getExpenseList() : Flow<List<ExpenseEntity>>

    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    suspend fun updateExpense(expenseEntity: ExpenseEntity)

    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    suspend fun getExpenseList(data: String) : Flow<List<ExpenseEntity>>

    fun getExpenseById(id: Int): Flow<ExpenseEntity>

    suspend fun getExpenseByCreated(date1: Long, date2: Long) : List<ExpenseEntity>

}
