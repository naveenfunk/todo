package com.todo.ui.login

import android.util.Patterns
import androidx.lifecycle.*
import com.todo.R
import com.todo.data.cloud.CloudRepository
import com.todo.data.cloud.CloudResponse
import com.todo.data.cloud.models.LoginResponse
import com.todo.utils.Result

import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(private val loginRepository: CloudRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<CloudResponse<LoginResponse>>()
    val loginResult: LiveData<CloudResponse<LoginResponse>> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val result = loginRepository.login(username, password)) {
                is Result.Success -> _loginResult.value =
                    CloudResponse.Success(result.data!!)
                is Result.Failure -> _loginResult.value =
                    CloudResponse.Failure(R.string.login_failed, Exception("Login failed"))
                is Result.NetworkError -> _loginResult.value =
                    CloudResponse.Failure(R.string.no_internet_error, Exception("Internet is not connected"))
            }
        }

    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    class LoginViewModelFactory(private val repo: CloudRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return return LoginViewModel(repo) as T
        }
    }
}