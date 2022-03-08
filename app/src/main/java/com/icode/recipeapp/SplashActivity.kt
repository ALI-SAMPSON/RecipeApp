package com.icode.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.icode.recipeapp.database.RecipeDatabase
import com.icode.recipeapp.entities.Category
import com.icode.recipeapp.entities.Meal
import com.icode.recipeapp.entities.MealItems
import com.icode.recipeapp.interfaces.GetDataService
import com.icode.recipeapp.retrofitclient.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks, EasyPermissions.PermissionCallbacks {

    private  var READ_STORAGE_PERM = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        readStorageTask()
        btnGetStarted.setOnClickListener {
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun getCategories(){
        val service = RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object: Callback<Category>{
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                loader.visibility = View.INVISIBLE
                for (category in response.body()!!.categoryItems!!){
                    getMeal(category.strcategory)
                }
                insertDataIntoRoomDb(response.body())
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                loader.visibility = View.INVISIBLE
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMeal(categoryName: String){
        val service = RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object: Callback<Meal>{

            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                loader.visibility = View.INVISIBLE
                insertMealIntoRoomDb(categoryName, response.body())
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                loader.visibility = View.INVISIBLE
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun insertDataIntoRoomDb(category: Category?) {
        launch {
            this.let {
                for(cat in category!!.categoryItems!!){
                    RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().insertCategory(cat)
                }
            }
        }
    }

    fun insertMealIntoRoomDb(categoryName: String, meal: Meal?) {
        launch {
            this.let {
                for(mealItem in meal!!.mealItems!!){
                    val mealItemModel = MealItems(
                        id = mealItem.id,
                        idMeal = mealItem.idMeal,
                        categoryName,
                        strMeal = mealItem.strMeal,
                        strMealThumb = mealItem.strMealThumb
                    )
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertMeal(mealItemModel)
                    Log.d("mealData", mealItem.toString())
                }
                btnGetStarted.visibility = View.VISIBLE
            }
        }
    }

    fun clearDatabase(){
        launch {
            this.let {
                RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().clearDb()
            }
        }
    }

    private fun hasReadStoragePermission() : Boolean{
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun readStorageTask(){
        if(hasReadStoragePermission()){
            clearDatabase()
            getCategories()
        }else{
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage",
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleAccepted(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onRationaleDenied(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        clearDatabase()
        getCategories()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
    }

}