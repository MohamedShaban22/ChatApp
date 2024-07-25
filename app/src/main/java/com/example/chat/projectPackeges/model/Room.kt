package com.example.chat.projectPackeges.model

import android.os.Parcelable
import androidx.versionedparcelable.ParcelImpl
import com.example.chat.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id:String?=null,
    val tittle:String?=null,
    val description:String?=null,
    val ownerId:String?=null,
    val categoryId:Int?=null
): Parcelable{
    fun getRoomImageById(): Int {
        return  when(categoryId){
            1-> R.drawable.sports
            2->R.drawable.music
            3->R.drawable.movies
            else ->R.drawable.sports
        }
    }

    companion object{
        const val collectionName="Rooms"
    }
}
