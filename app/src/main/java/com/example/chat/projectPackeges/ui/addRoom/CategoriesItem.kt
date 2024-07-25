package com.example.chat.projectPackeges.ui.addRoom

import com.example.chat.R

data class CategoriesItem(
    var id:Int,
    var categoryName:String,
    var categoryImage: Int
){
    companion object{
        fun getCategories()= listOf<CategoriesItem>(
            CategoriesItem(1,"Sports", R.drawable.sports),
            CategoriesItem(2,"Music", R.drawable.music),
            CategoriesItem(3,"Movies", R.drawable.movies),
            )
    }
}

