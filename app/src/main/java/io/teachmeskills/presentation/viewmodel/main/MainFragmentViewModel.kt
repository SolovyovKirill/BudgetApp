package io.teachmeskills.presentation.viewmodel.main

import androidx.lifecycle.*
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.domain.usecase.deleteexpense.DeleteExpenseUseCase
import io.teachmeskills.domain.usecase.getexpenselist.GetExpenseListUseCase
import io.teachmeskills.domain.usecase.updateexpense.UpdateExpenseUseCase
import io.teachmeskills.presentation.view.addexpense.AddExpenseFragment
import io.teachmeskills.utils.Expenses
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.*

class MainFragmentViewModel(
    private val getExpenseListUseCase: GetExpenseListUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    private val dateFlow: MutableStateFlow<String> = MutableStateFlow(
        AddExpenseFragment.dateFormatter.format(
            Date()
        )
    )

    val expensesListLiveData: LiveData<List<ExpenseEntity>> = dateFlow.flatMapLatest {
        getExpenseListUseCase.getExpenseList(it)
    }.asLiveData()

    fun setDate(data: String) {
        dateFlow.compareAndSet(dateFlow.value, data)
    }


}