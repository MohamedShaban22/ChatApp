package com.example.chat.projectPackeges.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.projectPackeges.fireStore.SessionProvider
import com.example.chat.projectPackeges.model.User
import com.example.chat.projectPackeges.fireStore.UsersDao
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel:ViewModel() {
    var password = MutableLiveData<String?>("123456")
    var email = MutableLiveData<String?>("mohamedragb01097434995@gmail.com")


    var passwordError = MutableLiveData<String?>()
    var emailError = MutableLiveData<String?>()
    var isLoading=MutableLiveData<Boolean>(false)
    var messageLiveData=MutableLiveData<String>()
    var event=MutableLiveData<LoginViewEvent>()

    private val auth =Firebase.auth
    fun login() {
        if (!isValidForm()) return
        isLoading.postValue(true)
        auth.signInWithEmailAndPassword(email.value!!,password.value!!)
                .addOnCompleteListener{task->
                    isLoading.postValue(false)
                    if (task.isSuccessful){

                        getUserFromDB(task.result.user!!.uid)
                      //  messageLiveData.postValue("Login Successful")
                    }
                    else{
                        messageLiveData.postValue(task.exception?.localizedMessage)
                    }
                }


    }
    private fun getUserFromDB(userId:String) {
        UsersDao.getUser(userId){ task->

            if (task.isSuccessful){
                SessionProvider.user=task.result.toObject(User::class.java)
                navigateToHome()
            }
            else{
                messageLiveData.postValue(task.exception?.localizedMessage ?: "error when create user")
            }

        }
    }
    fun navigateToHome(){
        event.postValue(LoginViewEvent.NavigateToHome)
    }
    fun navigateToRegister(){
        event.postValue(LoginViewEvent.NavigateToRegister)
    }

    private fun isValidForm(): Boolean {
        var valid = true

        if (password.value.isNullOrBlank()) {
            passwordError.postValue("password error")
            valid = false
        } else {
            passwordError.postValue(null)
        }

        if (email.value.isNullOrBlank()) {
            emailError.postValue("email error")
            valid = false
        } else {
            emailError.postValue(null)
        }

        return valid
    }
}