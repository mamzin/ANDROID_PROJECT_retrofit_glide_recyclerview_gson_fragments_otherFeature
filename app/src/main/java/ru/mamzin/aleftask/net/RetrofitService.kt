package ru.mamzin.aleftask.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://dev-tasks.alef.im/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}