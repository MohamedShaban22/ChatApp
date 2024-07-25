package com.example.chat.projectPackeges.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.projectPackeges.common.SingleLiveEvent
import com.example.chat.projectPackeges.fireStore.RoomsDao
import com.example.chat.projectPackeges.fireStore.SessionProvider
import com.example.chat.projectPackeges.model.Room
import com.example.chat.projectPackeges.ui.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class HomeViewModel : ViewModel() {
    var event = SingleLiveEvent<HomeViewEvent>()
    val rooms = MutableLiveData<List<Room>>()
    val filteredRooms = MutableLiveData<List<Room>>()
    val messageLiveData = MutableLiveData<Message>()
    val userNameLiveData = SingleLiveEvent<String?>()

    fun userName() {
        userNameLiveData.postValue(SessionProvider.user?.userName)
    }

    fun navigateToAddRoom() {
        event.postValue(HomeViewEvent.NavigateToAddRome)
    }

    fun getRooms() {
        RoomsDao.getRooms() { task ->
            if (task.isSuccessful) {
                val roomsList = task.result.toObjects(Room::class.java)
                rooms.postValue(roomsList)
                filteredRooms.postValue(roomsList)
            } else {
                messageLiveData.postValue(
                    Message(
                        message = task.exception?.localizedMessage,
                        posActionName = "OK"
                    )
                )
            }
        }
    }

    fun filterRooms(query: String) {
        val filteredList = rooms.value?.filter { room ->
            room.tittle!!.contains(query, ignoreCase = true)
        }
        filteredRooms.postValue(filteredList)
    }

    fun logout() {
        val auth = Firebase.auth
        auth.signOut()
        SessionProvider.user = null
        event.postValue(HomeViewEvent.NavigateToLogin)
    }
}
