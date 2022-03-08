package com.icode.recipeapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MealItems")
data class MealItems(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "idmeal")
    @Expose
    @SerializedName("idMeal")
    val idMeal: String,

    @ColumnInfo(name = "categoryName")
    val categoryName: String,

    @ColumnInfo(name = "strmeal")
    @Expose
    @SerializedName("strMeal")
    val strMeal: String,

    @ColumnInfo(name = "strmealthumb")
    @Expose
    @SerializedName("strMealThumb")
    val strMealThumb: String,



)