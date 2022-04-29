package ru.mamzin.aleftask.net

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("task-m-001/list.php")
    fun getImages(): Call<List<String>>

    companion object {
        private var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {

            val gson = GsonBuilder().setLenient().create()
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://dev-tasks.alef.im/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}