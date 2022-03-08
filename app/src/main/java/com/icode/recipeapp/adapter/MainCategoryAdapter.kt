package com.icode.recipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icode.recipeapp.R
import com.icode.recipeapp.entities.CategoryItems
import kotlinx.android.synthetic.main.item_rv_main_category.view.*

class MainCategoryAdapter: RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    var listener: OnItemClickListener? = null
    lateinit var context: Context
    var arrMainCategory = ArrayList<CategoryItems>()

    inner class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tv_dish_name = view.findViewById<TextView>(R.id.tv_dish_name)
        var img_dish = view.findViewById<ImageView>(R.id.img_dish)
    }

    fun setData(categoryItems: List<CategoryItems>){
        arrMainCategory = categoryItems as ArrayList<CategoryItems>
    }

    fun setClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        context = parent.context
        return  RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main_category, parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.itemView.tv_dish_name.text= arrMainCategory[position].strcategory
        Glide.with(context).load(arrMainCategory[position].strcategorythumb).into(holder.itemView.img_dish)
        holder.itemView.rootView.setOnClickListener{
            listener!!.onClicked(arrMainCategory[position].strcategory)
        }
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    interface OnItemClickListener{
        fun onClicked(categoryName: String)
    }

}