package io.teachmeskills.domain.repository


import io.teachmeskills.data.database.entity.Expense
import kotlinx.coroutines.flow.Flow


interface ExpenseRepository {

    fun getExpenseList() : Flow<List<Expense>>

    suspend fun insertExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun getExpenseList(date: String) : List<Expense>

//    suspend fun getExpenseById(expense: Expense): Expense?

}
