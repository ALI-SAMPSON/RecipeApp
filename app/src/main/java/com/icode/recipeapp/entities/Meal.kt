package com.icode.recipeapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.icode.recipeapp.entities.converter.MealListConverter

@Entity(tableName = "Meal")
class Meal (
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "mealItems")
    @Expose
    @SerializedName("meals")
    @TypeConverters(MealListConverter::class)
    var mealItems: List<MealItems>? = null
)
