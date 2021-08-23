package io.teachmeskills.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.usecase.deleteexpense.DeleteExpenseUseCase
import io.teachmeskills.domain.usecase.getexpensebyid.GetExpenseByIdUseCase
import io.teachmeskills.domain.usecase.getexpenselist.GetExpenseListUseCase
import io.teachmeskills.domain.usecase.getexpenselist1.GetExpenseListUseCase1
import io.teachmeskills.domain.usecase.insertexpense.InsertExpenseUseCase
import io.teachmeskills.domain.usecase.updateexpense.UpdateExpenseUseCase
import io.teachmeskills.presentation.view.addexpense.AddExpenseFragment
import io.teachmeskills.utils.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.*

class ExpenseViewModel(
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val getExpenseListUseCase: GetExpenseListUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    private val getExpenseListUseCase1: GetExpenseListUseCase1
) : ViewModel() {

    private val dateFlow: MutableStateFlow<String> = MutableStateFlow(
        AddExpenseFragment.dateFormatter.format(
            Date()
        )
    )
    val expensesListLiveData: LiveData<List<ExpenseEntity>> = dateFlow.flatMapLatest {
        getExpenseListUseCase.getExpenseList(it)
    }.asLiveData()

    private val _detailState = MutableStateFlow<DetailState>(DetailState.Loading)

    val detailState: StateFlow<DetailState> = _detailState

    fun updateExpense(expenseEntity: ExpenseEntity) =
        viewModelScope.launch {
            updateExpenseUseCase.updateExpense(expenseEntity)
        }

    fun insertExpense(expenseEntity: ExpenseEntity) =
        viewModelScope.launch {
            insertExpenseUseCase.insertExpense(expenseEntity)
        }

    fun deleteExpense(expenseEntity: ExpenseEntity) =
        viewModelScope.launch {
            deleteExpenseUseCase.deleteExpense(expenseEntity)
        }

    fun getByID(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading
        getExpenseByIdUseCase.getExpenseById(id).collect { result: ExpenseEntity? ->
            if (result != null) {
                _detailState.value = DetailState.Success(result)
            }
        }
    }

    fun getExpenseList() = viewModelScope.launch {
        getExpenseListUseCase1.getExpenseList()
    }

}