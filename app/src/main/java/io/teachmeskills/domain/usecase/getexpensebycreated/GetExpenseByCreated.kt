package io.teachmeskills.domain.usecase.getexpensebycreated

import io.teachmeskills.data.database.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface GetExpenseByCreated {

    suspend fun getExpenseByCreated(date1: Long, date2: Long) : List<ExpenseEntity>

}