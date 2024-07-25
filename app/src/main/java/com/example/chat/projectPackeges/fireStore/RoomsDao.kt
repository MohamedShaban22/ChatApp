package com.example.chat.projectPackeges.fireStore

import com.example.chat.projectPackeges.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore

object RoomsDao {

    fun getRoomsCollection(): CollectionReference {
        val dataBase=Firebase.firestore
        return dataBase.collection(Room.collectionName)
    }
    fun createRoom(tittle:String?,categoryId:Int?,
                   ownerId:String,desc:String? ,onCompleteListener:OnCompleteListener<Void>){
        val docRef=getRoomsCollection().document()
        val room=Room(
            tittle = tittle, description = desc, ownerId = ownerId, categoryId = categoryId, id = docRef.id
        )
        docRef.set(room).addOnCompleteListener(onCompleteListener)
    }
    fun getRooms(onCompleteListener: OnCompleteListener<QuerySnapshot>){
        getRoomsCollection().get().addOnCompleteListener(onCompleteListener)
    }

}