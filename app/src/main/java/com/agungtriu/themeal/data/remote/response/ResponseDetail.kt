package com.agungtriu.themeal.data.remote.response

import com.agungtriu.themeal.data.local.MealEntity
import com.google.gson.annotations.SerializedName

data class ResponseDetail(

    @field:SerializedName("meals")
    val meals: List<MealsItem>? = null
)

data class MealsItem(

    @field:SerializedName("strImageSource")
    val strImageSource: String? = null,

    @field:SerializedName("strIngredient10")
    val strIngredient10: String? = null,

    @field:SerializedName("strIngredient12")
    val strIngredient12: String? = null,

    @field:SerializedName("strIngredient11")
    val strIngredient11: String? = null,

    @field:SerializedName("strIngredient14")
    val strIngredient14: String? = null,

    @field:SerializedName("strCategory")
    val strCategory: String? = null,

    @field:SerializedName("strIngredient13")
    val strIngredient13: String? = null,

    @field:SerializedName("strIngredient16")
    val strIngredient16: String? = null,

    @field:SerializedName("strIngredient15")
    val strIngredient15: String? = null,

    @field:SerializedName("strIngredient18")
    val strIngredient18: String? = null,

    @field:SerializedName("strIngredient17")
    val strIngredient17: String? = null,

    @field:SerializedName("strArea")
    val strArea: String? = null,

    @field:SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String? = null,

    @field:SerializedName("strIngredient19")
    val strIngredient19: String? = null,

    @field:SerializedName("strTags")
    val strTags: String? = null,

    @field:SerializedName("idMeal")
    val idMeal: String? = null,

    @field:SerializedName("strInstructions")
    val strInstructions: String? = null,

    @field:SerializedName("strIngredient1")
    val strIngredient1: String? = null,

    @field:SerializedName("strIngredient3")
    val strIngredient3: String? = null,

    @field:SerializedName("strIngredient2")
    val strIngredient2: String? = null,

    @field:SerializedName("strIngredient20")
    val strIngredient20: String? = null,

    @field:SerializedName("strIngredient5")
    val strIngredient5: String? = null,

    @field:SerializedName("strIngredient4")
    val strIngredient4: String? = null,

    @field:SerializedName("strIngredient7")
    val strIngredient7: String? = null,

    @field:SerializedName("strIngredient6")
    val strIngredient6: String? = null,

    @field:SerializedName("strIngredient9")
    val strIngredient9: String? = null,

    @field:SerializedName("strIngredient8")
    val strIngredient8: String? = null,

    @field:SerializedName("strMealThumb")
    val strMealThumb: String? = null,

    @field:SerializedName("strMeasure20")
    val strMeasure20: String? = null,

    @field:SerializedName("strYoutube")
    val strYoutube: String? = null,

    @field:SerializedName("strMeal")
    val strMeal: String? = null,

    @field:SerializedName("strMeasure12")
    val strMeasure12: String? = null,

    @field:SerializedName("strMeasure13")
    val strMeasure13: String? = null,

    @field:SerializedName("strMeasure10")
    val strMeasure10: String? = null,

    @field:SerializedName("strMeasure11")
    val strMeasure11: String? = null,

    @field:SerializedName("dateModified")
    val dateModified: String? = null,

    @field:SerializedName("strDrinkAlternate")
    val strDrinkAlternate: String? = null,

    @field:SerializedName("strSource")
    val strSource: String? = null,

    @field:SerializedName("strMeasure9")
    val strMeasure9: String? = null,

    @field:SerializedName("strMeasure7")
    val strMeasure7: String? = null,

    @field:SerializedName("strMeasure8")
    val strMeasure8: String? = null,

    @field:SerializedName("strMeasure5")
    val strMeasure5: String? = null,

    @field:SerializedName("strMeasure6")
    val strMeasure6: String? = null,

    @field:SerializedName("strMeasure3")
    val strMeasure3: String? = null,

    @field:SerializedName("strMeasure4")
    val strMeasure4: String? = null,

    @field:SerializedName("strMeasure1")
    val strMeasure1: String? = null,

    @field:SerializedName("strMeasure18")
    val strMeasure18: String? = null,

    @field:SerializedName("strMeasure2")
    val strMeasure2: String? = null,

    @field:SerializedName("strMeasure19")
    val strMeasure19: String? = null,

    @field:SerializedName("strMeasure16")
    val strMeasure16: String? = null,

    @field:SerializedName("strMeasure17")
    val strMeasure17: String? = null,

    @field:SerializedName("strMeasure14")
    val strMeasure14: String? = null,

    @field:SerializedName("strMeasure15")
    val strMeasure15: String? = null
) {
    fun toMealEntity(): MealEntity {
        return MealEntity(
            idMeal = this.idMeal ?: System.currentTimeMillis().toString(),
            strMeal = this.strMeal,
            strImageSource = this.strImageSource,
            strCategory = this.strCategory,
            strArea = this.strArea,
            strCreativeCommonsConfirmed = this.strCreativeCommonsConfirmed,
            strTags = this.strTags,
            strInstructions = this.strInstructions,
            strIngredient1 = this.strIngredient1,
            strIngredient2 = this.strIngredient2,
            strIngredient3 = this.strIngredient3,
            strIngredient4 = this.strIngredient4,
            strIngredient5 = this.strIngredient5,
            strIngredient6 = this.strIngredient6,
            strIngredient7 = this.strIngredient7,
            strIngredient8 = this.strIngredient8,
            strIngredient9 = this.strIngredient9,
            strIngredient10 = this.strIngredient10,
            strIngredient11 = this.strIngredient11,
            strIngredient12 = this.strIngredient12,
            strIngredient13 = this.strIngredient13,
            strIngredient14 = this.strIngredient14,
            strIngredient15 = this.strIngredient15,
            strIngredient16 = this.strIngredient16,
            strIngredient17 = this.strIngredient17,
            strIngredient18 = this.strIngredient18,
            strIngredient19 = this.strIngredient19,
            strIngredient20 = this.strIngredient20,
            strMealThumb = this.strMealThumb,
            strYoutube = this.strYoutube,
            dateModified = this.dateModified,
            strDrinkAlternate = this.strDrinkAlternate,
            strSource = this.strSource,
            strMeasure1 = this.strMeasure1,
            strMeasure2 = this.strMeasure2,
            strMeasure3 = this.strMeasure3,
            strMeasure4 = this.strMeasure4,
            strMeasure5 = this.strMeasure5,
            strMeasure6 = this.strMeasure6,
            strMeasure7 = this.strMeasure7,
            strMeasure8 = this.strMeasure8,
            strMeasure9 = this.strMeasure9,
            strMeasure10 = this.strMeasure10,
            strMeasure11 = this.strMeasure11,
            strMeasure12 = this.strMeasure12,
            strMeasure13 = this.strMeasure13,
            strMeasure14 = this.strMeasure14,
            strMeasure15 = this.strMeasure15,
            strMeasure16 = this.strMeasure16,
            strMeasure17 = this.strMeasure17,
            strMeasure18 = this.strMeasure18,
            strMeasure19 = this.strMeasure19,
            strMeasure20 = this.strMeasure20
        )
    }
}
