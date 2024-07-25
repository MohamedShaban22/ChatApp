package com.example.chat.projectPackeges.ui.addRoom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.projectPackeges.fireStore.RoomsDao
import com.example.chat.projectPackeges.fireStore.SessionProvider
import com.example.chat.projectPackeges.model.Room
import com.example.chat.projectPackeges.ui.Message

class AddRoomViewModel:ViewModel() {
    var roomName=MutableLiveData<String?>()
    var roomDesc=MutableLiveData<String?>()
    val roomNameError = MutableLiveData<String>()
    val roomDescError = MutableLiveData<String>()
    val messageLiveData = MutableLiveData<Message>()
    val events = MutableLiveData<AddRoomViewEvent>()
    val categories=CategoriesItem.getCategories()
    var categorySelected= categories[0]

    fun createRome(){
        if(!validForm())return
        RoomsDao.createRoom(roomName.value,categorySelected.id,
            SessionProvider.user?.id ?:"" , roomDesc.value){task->
            if (task.isSuccessful){
//                messageLiveData.postValue(Message(message = "Room Created Successful",
//                    posActionName = "Ok", onPosActionClick = {
//                        events.postValue(AddRoomViewEvent.NavigateToHomeAndFinish)
//                    }))

                events.postValue(AddRoomViewEvent.NavigateToHomeAndFinish)
            }
            else{
                messageLiveData.postValue(Message(message = "error: "+task.exception?.localizedMessage,
                    posActionName = "Ok"))
            }
        }
    }

    private fun validForm(): Boolean {
        var isValid=true
        if(roomName.value.isNullOrBlank()){
            isValid=false
            roomNameError.postValue("Enter Name")
        }else{
            roomNameError.postValue(null)

        }
        if(roomDesc.value.isNullOrBlank()){
            isValid=false
            roomDescError.postValue("Enter Description")
        }else{
            roomDescError.postValue(null)        }
        return isValid
    }

    fun onCategorySelected(position: Int) {
        categorySelected=categories[position]

    }

}