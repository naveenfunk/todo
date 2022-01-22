package com.todo.data.cloud

import com.todo.data.cloud.models.LoginResponse
import com.todo.utils.Result
import com.todo.utils.SuspendingCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("login")
    suspend fun login(@Body parameters : Map<String, String>): Result<LoginResponse>

    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(SuspendingCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}