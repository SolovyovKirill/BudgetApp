package io.teachmeskills.domain.usecase.getexpenselist

import io.teachmeskills.utils.Expenses
import kotlinx.coroutines.flow.Flow

interface GetExpenseListUseCase {

    suspend fun getExpenseList(date: String): List<Expenses>

}