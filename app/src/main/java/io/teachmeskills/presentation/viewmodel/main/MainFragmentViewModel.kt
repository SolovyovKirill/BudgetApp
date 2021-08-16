package io.teachmeskills.presentation.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.teachmeskills.domain.usecase.getexpenselist.GetExpenseListUseCase
import io.teachmeskills.utils.Expenses
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val getExpenseListUseCase: GetExpenseListUseCase
): ViewModel() {

    val expensesListLiveData: MutableLiveData<List<Expenses>> =
        MutableLiveData()

    fun getList(date: String) {
        viewModelScope.launch(IO) {
            expensesListLiveData.postValue(getExpenseListUseCase.getExpenseList(date))
        }
    }
}