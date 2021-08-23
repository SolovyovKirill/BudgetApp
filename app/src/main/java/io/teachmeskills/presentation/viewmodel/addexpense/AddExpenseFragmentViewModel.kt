package io.teachmeskills.presentation.viewmodel.addexpense


import androidx.lifecycle.*
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.usecase.deleteexpense.DeleteExpenseUseCase
import io.teachmeskills.domain.usecase.insertexpense.InsertExpenseUseCase
import kotlinx.coroutines.launch


class AddExpenseFragmentViewModel(
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
) : ViewModel() {

    fun addExpenseToDatabase(expenseEntity: ExpenseEntity) =
        viewModelScope.launch {
            insertExpenseUseCase.insertExpense(expenseEntity)
        }

    fun deleteExpense(expenseEntity: ExpenseEntity) =
        viewModelScope.launch {
            deleteExpenseUseCase.deleteExpense(expenseEntity)
        }
}



