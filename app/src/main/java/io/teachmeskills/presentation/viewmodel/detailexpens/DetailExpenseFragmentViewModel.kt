package io.teachmeskills.presentation.viewmodel.detailexpens


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.usecase.deleteexpense.DeleteExpenseUseCase
import io.teachmeskills.domain.usecase.getexpensebyid.GetExpenseByIdUseCase
import io.teachmeskills.domain.usecase.updateexpense.UpdateExpenseUseCase
import kotlinx.coroutines.launch

class DetailExpenseFragmentViewModel(
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase
) : ViewModel() {

    val expenseLiveData: LiveData<ExpenseEntity> = MutableLiveData()

    fun updateExpense(expenseEntity: ExpenseEntity) = viewModelScope.launch {
        updateExpenseUseCase.updateExpense(expenseEntity)
    }

    fun getByID(id: Int) = viewModelScope.launch {
        getExpenseByIdUseCase.getExpenseById(id)
    }

    fun deleteExpense(expenseEntity: ExpenseEntity) =
        viewModelScope.launch {
            deleteExpenseUseCase.deleteExpense(expenseEntity)
        }


}