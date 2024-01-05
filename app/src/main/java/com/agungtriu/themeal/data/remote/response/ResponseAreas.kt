package com.agungtriu.themeal.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseAreas(

    @field:SerializedName("meals")
    val meals: List<AreaItem>? = null
)

data class AreaItem(

    @field:SerializedName("strArea")
    val strArea: String? = null
)
