package com.todo.data.cloud

import androidx.annotation.StringRes
import java.lang.Exception

sealed class CloudResponse<out T> {
    class Success<out T>(val data: T) : CloudResponse<T>()
    class Failure(@StringRes val errorMessage: Int,val exception: Exception) : CloudResponse<Nothing>()
}