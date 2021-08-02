package io.teachmeskills.utils

import android.util.Patterns

fun isNameValid(name: String): Boolean = name.isNotEmpty()

fun isSurnameValid(surname: String) : Boolean = surname.isNotEmpty()

fun isEmailValid(email: String) : Boolean {
    val emailPattern = Patterns.EMAIL_ADDRESS
    return emailPattern.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean = password.length >= 6