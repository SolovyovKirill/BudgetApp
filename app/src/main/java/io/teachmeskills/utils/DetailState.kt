package io.teachmeskills.utils

import io.teachmeskills.data.database.entity.ExpenseEntity

sealed class DetailState {
    object Loading : DetailState()
    object Empty : DetailState()
    data class Success(val expense : ExpenseEntity) : DetailState()
    data class Error(val exception : Throwable) : DetailState()

}