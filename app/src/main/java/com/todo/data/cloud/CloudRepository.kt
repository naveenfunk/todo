package com.todo.data.cloud

import com.todo.data.cloud.models.LoginResponse
import com.todo.utils.Result

interface CloudRepository {

    suspend fun login(email :String, password : String) : Result<LoginResponse>
}