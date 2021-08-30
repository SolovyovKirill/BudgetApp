package io.teachmeskills.utils

import io.teachmeskills.data.database.entity.ExpenseEntity

sealed class ViewState {
    object Loading : ViewState()
    object Empty : ViewState()
    data class Success(val expenses: List<ExpenseEntity>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
