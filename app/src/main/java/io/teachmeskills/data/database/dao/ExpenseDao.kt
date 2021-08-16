package io.teachmeskills.data.database.dao

import androidx.room.*
import io.teachmeskills.data.database.entity.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM  expenses ORDER BY amount")
    fun getExpenseList(): Flow<List<Expense>>

    @Query("SELECT * FROM  expenses WHERE date LIKE :date ORDER BY amount")
    suspend fun getExpenseList(date: String): List<Expense>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpense(expense: Expense)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

//    @Query("SELECT * FROM expenses WHERE id == :expenseId LIMIT 1")
//    suspend fun getExpenseById(expenseId: Int): Expense?
}