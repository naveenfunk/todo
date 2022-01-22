package com.todo.data.cloud

class RetrofitRepository(val retrofitService: RetrofitService) : CloudRepository{

    override suspend fun login(email: String, password: String) = retrofitService.login(mapOf("email" to email,"password" to password))
}