package com.example.chat.projectPackeges.fireStore

import com.example.chat.projectPackeges.model.Message
import com.google.android.gms.tasks.OnCompleteListener

object MessageDao {
    fun getMessagesCollection(roomId:String)=
        RoomsDao.getRoomsCollection()
            .document(roomId)//room document
            .collection(Message.collectionName)//create collection messages in this room

    fun sendMessage(message:Message,onCompleteListener: OnCompleteListener<Void>){
        val messageDoc= getMessagesCollection(message.roomId ?:"")
            .document() //create document for every message
        message.id=messageDoc.id
        if ( message.content .isNullOrBlank())return
        else{
            messageDoc.set(message).addOnCompleteListener(onCompleteListener) //set message data to this message doc that he create it
        }

    }

}