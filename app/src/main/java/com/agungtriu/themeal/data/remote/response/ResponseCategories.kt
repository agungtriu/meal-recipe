package com.agungtriu.themeal.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseCategories(

    @field:SerializedName("meals")
    val meals: List<CategoryItem>? = null
)

data class CategoryItem(

    @field:SerializedName("strCategory")
    val strCategory: String? = null
)
