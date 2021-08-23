package io.teachmeskills.domain.usecase.getexpenselist1

import io.teachmeskills.data.database.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface GetExpenseListUseCase1 {

    suspend fun getExpenseList(): Flow<List<ExpenseEntity>>

}