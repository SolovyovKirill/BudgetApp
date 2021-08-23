package io.teachmeskills.data.database.dao

import androidx.room.*
import io.teachmeskills.data.database.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM  expenses ORDER BY amount")
    fun getExpenseList(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM  expenses WHERE date LIKE :data ORDER BY amount")
    fun getExpenseList(data: String): Flow<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: Int) : Flow<ExpenseEntity>
}