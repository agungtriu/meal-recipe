package com.agungtriu.themeal.data.remote

import com.agungtriu.themeal.data.remote.response.ResponseAreas
import com.agungtriu.themeal.data.remote.response.ResponseCategories
import com.agungtriu.themeal.data.remote.response.ResponseDetail
import com.agungtriu.themeal.data.remote.response.ResponseFilter
import com.agungtriu.themeal.data.remote.response.ResponseSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search.php")
    suspend fun getSearch(@Query("s") key: String? = ""): ResponseSearch

    @GET("filter.php")
    suspend fun getFilter(
        @Query("c") category: String?,
        @Query("a") area: String?
    ): ResponseFilter

    @GET("list.php?c=list")
    suspend fun getCategories(): ResponseCategories

    @GET("list.php?a=list")
    suspend fun getAreas(): ResponseAreas

    @GET("lookup.php")
    suspend fun getDetail(@Query("i") id: String): ResponseDetail

}