package com.example.chat.projectPackeges.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.projectPackeges.fireStore.SessionProvider
import com.example.chat.projectPackeges.fireStore.UsersDao
import com.example.chat.projectPackeges.model.User
import com.example.chat.projectPackeges.ui.login.LoginViewEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.toObject

class SplashViewModel: ViewModel() {
    var event= MutableLiveData<SplashViewEvent>()

    fun navigate() {
        if (Firebase.auth.currentUser==null){
            event.postValue(SplashViewEvent.NavigateToLogin)
            return
        }
        UsersDao.getUser(
            Firebase.auth.uid
        ){
            if (!it.isSuccessful){
                event.postValue(SplashViewEvent.NavigateToLogin)
                return@getUser
            }
            SessionProvider.user=it.result.toObject(User::class.java)
            event.postValue(SplashViewEvent.NavigateToHome)
        }


    }
}