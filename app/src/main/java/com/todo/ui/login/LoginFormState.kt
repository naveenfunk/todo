package com.todo.ui.login

data class LoginFormState(
    var usernameError : Int? = null, var passwordError : Int? = null, var isDataValid : Boolean = false
)