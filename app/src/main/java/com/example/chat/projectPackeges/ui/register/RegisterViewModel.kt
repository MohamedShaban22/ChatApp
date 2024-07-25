package com.example.chat.projectPackeges.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.projectPackeges.fireStore.SessionProvider
import com.example.chat.projectPackeges.fireStore.UsersDao
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.chat.projectPackeges.model.User

class RegisterViewModel :ViewModel() {

    var name=MutableLiveData<String?>()
    var password=MutableLiveData<String?>()
    var passwordConfirmation=MutableLiveData<String?>()
    var email=MutableLiveData<String?>()


    var nameError=MutableLiveData<String?>()
    var passwordError=MutableLiveData<String?>()
    var passwordConfirmationError=MutableLiveData<String?>()
    var emailError=MutableLiveData<String?>()

    var isLoading=MutableLiveData<Boolean>(false)
    var messageLiveData=MutableLiveData<String>()
    var event=MutableLiveData<RegisterViewEvent>()

    private val auth = Firebase.auth
    fun register(){
    if ( !isValidForm()) return
        else {
        isLoading.postValue(true)
        auth.createUserWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener { task ->
                isLoading.postValue(false)

                if (task.isSuccessful) {
                    insertUserToFireStore(task.result.user?.uid?:"")

                   // messageLiveData.postValue("register successful \n userId = ${task.result.user?.uid} ")

                } else {
                    messageLiveData.postValue(task.exception?.localizedMessage ?: "something error")
                }
            }
        }
    }

    private fun insertUserToFireStore(uid: String) {
        val user= User(id=uid ,
            userName = name.value
            , email = email.value
            )
        UsersDao.createUser(user){task->
            if (task.isSuccessful){
                SessionProvider.user=user
                navigateToHome()
            }
            else{
                messageLiveData.postValue(task.exception?.localizedMessage ?: "error when create user")

            }
        }
    }


    fun navigateToHome(){
        event.postValue(RegisterViewEvent.NavigateToHome)
    }
    fun navigateToLogin(){
        event.postValue(RegisterViewEvent.NavigateToLogin)
    }
    private fun isValidForm(): Boolean {
        var valid = true

        if (name.value.isNullOrBlank()) {
            nameError.postValue("user name error")
            valid = false
        } else {
            nameError.postValue(null)
        }

        if (password.value.isNullOrBlank()) {
            passwordError.postValue("password error")
            valid = false
        } else {
            passwordError.postValue(null)
        }

        if (passwordConfirmation.value.isNullOrBlank() ||
            passwordConfirmation.value !=password.value) {
            passwordConfirmationError.postValue("password confirmation error")
            valid = false
        } else {
            passwordConfirmationError.postValue(null)
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