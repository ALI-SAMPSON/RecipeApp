package com.icode.recipeapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.icode.recipeapp.entities.Category
import com.icode.recipeapp.entities.CategoryItems
import com.icode.recipeapp.entities.MealItems

@Dao
interface RecipeDao {

    @Query("SELECT * FROM categoryitems ORDER BY id DESC")
    suspend fun getAllCategories() : List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryItems)

    @Query("SELECT * FROM mealitems WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName: String) : List<MealItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealItems: MealItems)

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()

}