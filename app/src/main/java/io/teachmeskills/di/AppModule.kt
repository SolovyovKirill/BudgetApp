package io.teachmeskills.di

import io.teachmeskills.data.database.AppDatabase
import io.teachmeskills.data.repository.ExpenseRepositoryImpl
import io.teachmeskills.domain.repository.ExpenseRepository
import io.teachmeskills.domain.usecase.deleteexpense.DeleteExpenseUseCase
import io.teachmeskills.domain.usecase.deleteexpense.DeleteExpenseUseCaseImpl
import io.teachmeskills.domain.usecase.getexpensebyid.GetExpenseByIdUseCase
import io.teachmeskills.domain.usecase.getexpensebyid.GetExpenseByIdUseCaseImpl
import io.teachmeskills.domain.usecase.getexpenselist.GetExpenseListUseCase
import io.teachmeskills.domain.usecase.getexpenselist.GetExpenseListUseCaseImpl
import io.teachmeskills.domain.usecase.getexpenselist1.GetExpenseListUseCase1
import io.teachmeskills.domain.usecase.getexpenselist1.GetExpenseListUseCase1Impl
import io.teachmeskills.domain.usecase.insertexpense.InsertExpenseUseCase
import io.teachmeskills.domain.usecase.insertexpense.InsertExpenseUseCaseImpl
import io.teachmeskills.domain.usecase.updateexpense.UpdateExpenseUseCase
import io.teachmeskills.domain.usecase.updateexpense.UpdateExpenseUseCaseImpl
import io.teachmeskills.presentation.viewmodel.addexpense.AddExpenseFragmentViewModel
import io.teachmeskills.presentation.viewmodel.detailexpens.DetailExpenseFragmentViewModel
import io.teachmeskills.presentation.viewmodel.editexpense.EditExpenseViewModel
import io.teachmeskills.presentation.viewmodel.main.MainFragmentViewModel
import io.teachmeskills.presentation.viewmodel.ExpenseViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val koinModuleApp = module {

    single { AppDatabase.getInstance(androidContext()) }

    single {
        get<AppDatabase>().getExpenseDao()
    }

    single<ExpenseRepository> {
        ExpenseRepositoryImpl(get())
    }

    single<DeleteExpenseUseCase> {
        DeleteExpenseUseCaseImpl(get())
    }

    single<InsertExpenseUseCase> {
        InsertExpenseUseCaseImpl(get())
    }

    single<UpdateExpenseUseCase> {
        UpdateExpenseUseCaseImpl(get())
    }

    single<GetExpenseListUseCase> {
        GetExpenseListUseCaseImpl(get())
    }

    single<GetExpenseByIdUseCase> {
        GetExpenseByIdUseCaseImpl(get())
    }

    single<GetExpenseListUseCase1> {
        GetExpenseListUseCase1Impl(get())
    }

    factory {
        AddExpenseFragmentViewModel(get(), get())
    }

    factory {
        MainFragmentViewModel(get(), get(), get())
    }
    factory {
        DetailExpenseFragmentViewModel(get(), get(), get())
    }
    factory {
        EditExpenseViewModel(get())
    }

    factory {
        ExpenseViewModel(get(),get(),get(),get(),get(),get())
    }
}
