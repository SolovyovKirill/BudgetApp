package io.teachmeskills.presentation.viewmodel.editexpense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.usecase.updateexpense.UpdateExpenseUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class EditExpenseViewModel(
   private val updateExpenseUseCase: UpdateExpenseUseCase
) : ViewModel() {


    fun updateExpense(expenseEntity: ExpenseEntity) =
        viewModelScope.launch {
            updateExpenseUseCase.updateExpense(expenseEntity)
        }

}


