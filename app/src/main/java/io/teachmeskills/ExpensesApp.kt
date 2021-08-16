package io.teachmeskills

import android.app.Application
import io.teachmeskills.di.koinModuleApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin

@KoinApiExtension
class ExpensesApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ExpensesApp)
            modules(listOf(koinModuleApp))
        }
    }
}