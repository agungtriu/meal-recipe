package com.agungtriu.themeal.data.remote.response

import com.agungtriu.themeal.utils.MealItem
import com.google.gson.annotations.SerializedName

data class ResponseFilter(

    @field:SerializedName("meals")
    val meals: List<FilterItem>? = null
)

data class FilterItem(

    @field:SerializedName("strMealThumb")
    val strMealThumb: String? = null,

    @field:SerializedName("idMeal")
    val idMeal: String? = null,

    @field:SerializedName("strMeal")
    val strMeal: String? = null
) {
    fun toMealEntity(): MealItem {
        return MealItem(
            id = this.idMeal,
            title = this.strMeal,
            image = this.strMealThumb
        )
    }
}
