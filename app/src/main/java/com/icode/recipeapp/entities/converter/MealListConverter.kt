package com.icode.recipeapp.entities.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.icode.recipeapp.entities.Category
import com.icode.recipeapp.entities.Meal
import com.icode.recipeapp.entities.MealItems

class MealListConverter {
    @TypeConverter
    fun fromMealList(meal: List<MealItems>): String? {
        if(meal == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<MealItems>(){

            }.type
            return gson.toJson(meal,type)
        }
    }

    @TypeConverter
    fun toMealList(mealString: String): List<MealItems>?{
        if(mealString == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<MealItems>(){

            }.type
            return  gson.fromJson(mealString, type)
        }
    }

}