package io.teachmeskills.domain.usecase.getexpensebyid

import io.teachmeskills.data.database.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface GetExpenseByIdUseCase {

    fun getExpenseById(id:Int) : Flow<ExpenseEntity>

}