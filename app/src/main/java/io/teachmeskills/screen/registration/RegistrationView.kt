package io.teachmeskills.screen.registration

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface RegistrationView: MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNameIsEmptyError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmailIsEmptyError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showInvalidEmailError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPasswordIsShortError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun registerWithEmailAndPassword(email: String, password: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showValidEmail()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showValidName()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showValidPassword()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openSignInScreen()
}