package io.teachmeskills.domain.usecase.getexpenselist

import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.utils.Expenses
import kotlinx.coroutines.flow.Flow

interface GetExpenseListUseCase {

    suspend fun getExpenseList(data: String): Flow<List<ExpenseEntity>>

}