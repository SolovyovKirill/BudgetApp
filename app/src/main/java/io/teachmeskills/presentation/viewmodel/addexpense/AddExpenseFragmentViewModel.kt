package io.teachmeskills.presentation.viewmodel.addexpense


import androidx.lifecycle.*
import io.teachmeskills.data.database.entity.Expense
import io.teachmeskills.domain.usecase.deleteexpense.DeleteExpenseUseCase
import io.teachmeskills.domain.usecase.getexpenselistflow.GetExpenseListFlowUseCase
import io.teachmeskills.domain.usecase.insertexpense.InsertExpenseUseCase
import kotlinx.coroutines.launch


class AddExpenseFragmentViewModel(
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getExpenseListFlowUseCase: GetExpenseListFlowUseCase
) : ViewModel() {

    private val expenseLiveData: LiveData<List<Expense>> =
        getExpenseListFlowUseCase.getExpenseList().asLiveData()

    fun addExpenseToDatabase(expense: Expense) =
        viewModelScope.launch {
            insertExpenseUseCase.insertExpense(expense)
        }

    fun deleteExpense(expense: Expense) =
        viewModelScope.launch {
            deleteExpenseUseCase.deleteExpense(expense)
        }
}



