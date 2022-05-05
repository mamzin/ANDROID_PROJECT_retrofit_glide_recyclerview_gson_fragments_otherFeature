package ru.mamzin.aleftask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mamzin.aleftask.utils.NetRepository

class MainViewModel constructor(private val repository: NetRepository) : ViewModel() {

    val dataList = MutableLiveData<List<String>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllImages() {
        val response = repository.getAllImages()

        response.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                dataList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}