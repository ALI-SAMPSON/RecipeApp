package com.icode.recipeapp.interfaces

import com.icode.recipeapp.entities.Category
import com.icode.recipeapp.entities.Meal
import com.icode.recipeapp.entities.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {

    @GET("categories.php")
    fun getCategoryList(): Call<Category>

    @GET("filter.php")
    fun getMealList( @Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getSpecificItem( @Query("i") id: String): Call<MealResponse>

}