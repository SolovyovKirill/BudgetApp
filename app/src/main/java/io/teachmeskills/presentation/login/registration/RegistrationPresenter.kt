package io.teachmeskills.presentation.login.registration

import android.util.Log
import io.teachmeskills.data.Result
import io.teachmeskills.domain.manager.UserManager
import io.teachmeskills.domain.usecase.GetUserByUidUseCase
import io.teachmeskills.domain.usecase.RegisterUserUseCase
import io.teachmeskills.screen.registration.RegistrationView
import io.teachmeskills.utils.isEmailValid
import io.teachmeskills.utils.isNameValid
import io.teachmeskills.utils.isPasswordValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter

private val <T> Result<T>.onError: Unit
    get() {}

@InjectViewState
class RegistrationPresenter(
    private val userManager: UserManager,
    private val getUserByUid: GetUserByUidUseCase,
    private val registerUserOnServer: RegisterUserUseCase
):MvpPresenter<RegistrationView>() {

    fun register(username:String, email:String, password:String){
        val areFieldsValid = validateFields(username, email, password)

        if(areFieldsValid) {
            viewState.registerWithEmailAndPassword(email, password)
        }

    }

    private fun validateFields(username: String, email: String, password: String): Boolean =
        when{
            !isNameValid(username)-> {
                viewState.showNameIsEmptyError()
                false
            }
            email.isEmpty() -> {
                viewState.showEmailIsEmptyError()
                false
            }
            !isEmailValid(email) -> {
                viewState.showInvalidEmailError()
                false
            }
            !isPasswordValid(password) -> {
                viewState.showPasswordIsShortError()
                false
            }
            else -> true
        }

    fun validateEmail(email:String) {
        if(!isEmailValid(email))
            viewState.showInvalidEmailError()
        else
            viewState.showValidName()
    }

    fun validateName(name:String) {
        if(!isNameValid(name))
            viewState.showNameIsEmptyError()
        else
            viewState.showValidName()
    }
    fun validatePassword(password: String) {
        if(!isPasswordValid(password))
            viewState.showPasswordIsShortError()
        else
            viewState.showValidPassword()
    }

    fun setUid (uid: String?) {
        userManager.uid=uid
    }

    fun registerUser(
        uid: String?,
        username: String,
        email: String
    ){
        CoroutineScope(IO).launch {
            registerUserOnServer(uid!!, username, email)
            getUserByUid(uid).onSuccess { userResult->
                userManager.userId = userResult.value.id
                withContext(Main) {
                    viewState.openSignInScreen()
                }
            }.onError {
                when(it) {
                    is Result.Error.UnknownError -> Log.e("Error ", "generic error")
                    is Result.Error.NetworkError -> Log.e("Error ", "network error")
                }
            }
        }
    }
}