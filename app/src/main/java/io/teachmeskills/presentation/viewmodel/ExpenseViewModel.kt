package io.teachmeskills.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.usecase.deleteexpense.DeleteExpenseUseCase
import io.teachmeskills.domain.usecase.getexpensebycreated.GetExpenseByCreated
import io.teachmeskills.domain.usecase.getexpensebyid.GetExpenseByIdUseCase
import io.teachmeskills.domain.usecase.getexpenselist.GetExpenseListUseCase
import io.teachmeskills.domain.usecase.getexpenselist1.GetExpenseListUseCase1
import io.teachmeskills.domain.usecase.insertexpense.InsertExpenseUseCase
import io.teachmeskills.domain.usecase.updateexpense.UpdateExpenseUseCase
import io.teachmeskills.presentation.view.addexpense.AddExpenseFragment
import io.teachmeskills.utils.DetailState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class ExpenseViewModel(
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val getExpenseListUseCase: GetExpenseListUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    private val getExpenseListUseCase1: GetExpenseListUseCase1,
    private val getExpenseByCreated: GetExpenseByCreated
) : ViewModel() {


    private val dateFlow: MutableStateFlow<String> = MutableStateFlow(
        AddExpenseFragment.dateFormatter.format(
            Date()
        )
    )

    val expensesListLiveData: LiveData<List<ExpenseEntity>> = dateFlow.flatMapLatest {
        getExpenseListUseCase.getExpenseList(it)
    }.asLiveData()


    val filteredListLiveData: MutableLiveData<List<ExpenseEntity>> = MutableLiveData()


    fun filterExpensesList(date1: Long, date2: Long) = viewModelScope.launch {
        filteredListLiveData.postValue(getExpenseByCreated.getExpenseByCreated(date1, date2))
    }


    // val filteredList: LiveData ???









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

//    fun a(e: Enum<*>) {
//        when (e) {
//            TODAY -> filterExpensesList()
//        }
//    }

    // Без бд
    private fun filterExpensesList() {
        // _data_1_ - millis Либо _data_1_ - days/month/year
        // _data_2_ - millis Либо _data_2_ - days/month/year

        //newList

        // for() // для expensesListLiveData
        //     val item_date_millis/date = ...
        //     if(item_date_millis/date > ... && < ...)
        //             newList.add(item)
        // ...
        // ...
        // filteredList.postValue(newList)
    }

    // Через бд
    private fun filterExpensesList(a: Int) {
        // _data_1_ - millis
        // _data_2_ - millis

        //  val list = filterUseCase.execute(_data_1_, _data_2_)
        // expensesListLiveData.postValue(list)
    }






}