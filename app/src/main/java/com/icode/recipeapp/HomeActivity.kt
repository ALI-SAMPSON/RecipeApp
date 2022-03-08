package com.icode.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.icode.recipeapp.adapter.MainCategoryAdapter
import com.icode.recipeapp.adapter.SubCategoryAdapter
import com.icode.recipeapp.database.RecipeDatabase
import com.icode.recipeapp.entities.CategoryItems
import com.icode.recipeapp.entities.MealItems
import com.icode.recipeapp.entities.Recipe
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {

    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealItems>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getDataFromDb()

        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClickedSubItem)

    }

    private val onClicked = object: MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }

    }

    private val onClickedSubItem = object: SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

    }

    private fun getDataFromDb(){
        launch {
            this.let {
                val cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategories()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()
                getMealDataFromDb(arrMainCategory[0].strcategory)
                mainCategoryAdapter.setData(arrMainCategory)
                rv_main_category.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_main_category.adapter = mainCategoryAdapter
            }
        }
    }

    private fun getMealDataFromDb(categoryName: String){
        tv_category_name.text = "$categoryName Category"
        launch {
            this.let {
                val cat = RecipeDatabase.getDatabase(this@HomeActivity)
                    .recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealItems>
                subCategoryAdapter.setData(arrSubCategory)
                rv_sub_category.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_sub_category.adapter = subCategoryAdapter
            }
        }
    }

}