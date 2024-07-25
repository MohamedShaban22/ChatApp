package com.example.chat.projectPackeges.model

data class User(
    val id:String?=null,
    val userName:String?=null,
    val email:String?=null
){
    companion object {
        const val collectionName="Users"
    }
}
