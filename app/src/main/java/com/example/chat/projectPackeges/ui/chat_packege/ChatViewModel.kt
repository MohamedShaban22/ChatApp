package com.example.chat.projectPackeges.ui.chat_packege

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.projectPackeges.common.SingleLiveEvent
import com.example.chat.projectPackeges.fireStore.MessageDao
import com.example.chat.projectPackeges.fireStore.SessionProvider
import com.example.chat.projectPackeges.model.Message
import com.example.chat.projectPackeges.model.Room
import com.google.firebase.firestore.DocumentChange

class ChatViewModel: ViewModel() {
    private var room: Room? = null
    val messageLiveData= SingleLiveEvent<String?>()
    val toastMessage= MutableLiveData<String?>()
    val newMessagesLiveData= SingleLiveEvent<List<Message>>()
    val roomNameLiveData= SingleLiveEvent<String?>()

    fun roomName(){
        roomNameLiveData.postValue(room?.tittle)
    }
    fun sendMessage(){
        if (room?.id == null) {
            toastMessage.postValue("Invalid room. Cannot send message.")
            return
        }
        val message=Message(
            content = messageLiveData.value?.trim(),
            senderName = SessionProvider.user?.userName,
            senderId = SessionProvider.user?.id,
            roomId = room?.id

        )
        MessageDao.sendMessage(message){
            if (it.isSuccessful){
                messageLiveData.value=""
            }
            else{
                toastMessage.postValue("some Thing went wrong try again")
            }
        }
    }
    fun changeRoom(room: Room?){
        this.room=room
        roomName()
        listenToMessages()
    }

    private fun listenToMessages(){
        val messageCollection=MessageDao.getMessagesCollection(room?.id?:"")
            .orderBy("time")
            .limit(70)
        messageCollection.addSnapshotListener {snapShot, e ->
//            snapShot?.documents?.forEach {
//                val messages= it.toObject(Message::class.java)
//
//            }
            val newMessages:MutableList<Message> = mutableListOf()
            snapShot?.documentChanges?.forEach {documentChange ->
                if (documentChange.type==DocumentChange.Type.ADDED){
                    val message =documentChange.document.toObject(Message::class.java)
                    newMessages.add(message)
                }
                else if(documentChange.type==DocumentChange.Type.MODIFIED){}
                else if(documentChange.type==DocumentChange.Type.REMOVED){}

            }
            newMessagesLiveData.postValue(newMessages)
        }
    }
}