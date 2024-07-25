package com.example.chat.projectPackeges.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class Message(
    var id:String?=null,
    val content:String?=null,
    val senderId:String?=null,
    val time:Timestamp?= Timestamp.now(),
    val senderName:String?=null,
    val read:Boolean?=null,
    val roomId:String?=null,

    ){
    companion object{
        const val collectionName="messages"
    }
    fun formatTime():String{
        val simpleDateFormat=SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(time?.toDate()?:"")
    }
}

