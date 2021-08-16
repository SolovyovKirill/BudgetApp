package io.teachmeskills.domain.usecase.getexpenselistflow

import io.teachmeskills.data.database.entity.Expense
import kotlinx.coroutines.flow.Flow

interface GetExpenseListFlowUseCase {

    fun getExpenseList() : Flow<List<Expense>>

}