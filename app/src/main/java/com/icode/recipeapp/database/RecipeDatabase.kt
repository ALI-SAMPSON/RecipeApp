package com.icode.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.icode.recipeapp.dao.RecipeDao
import com.icode.recipeapp.entities.*
import com.icode.recipeapp.entities.converter.CategoryListConverter
import com.icode.recipeapp.entities.converter.MealListConverter

@Database(entities = [Recipe::class, Category::class, CategoryItems::class, Meal::class, MealItems::class], version = 1, exportSchema = false)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    companion object{

        var recipeDatabase: RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase{
            if(recipeDatabase == null){
                recipeDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).build()
            }
            return recipeDatabase!!
        }

        fun destroyDatabase(){
            recipeDatabase = null
        }

    }

    abstract fun recipeDao() : RecipeDao

}