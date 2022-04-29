package ru.mamzin.aleftask

import ru.mamzin.aleftask.net.RetrofitService

class NetRepository(private val retrofitService: RetrofitService) {

    fun getAllImages() = retrofitService.getImages()

}