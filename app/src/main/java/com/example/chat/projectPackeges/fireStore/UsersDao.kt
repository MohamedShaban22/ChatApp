package com.example.chat.projectPackeges.fireStore

import com.example.chat.projectPackeges.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

object UsersDao {
    private fun getUsersCollection():CollectionReference{
        val database=Firebase.firestore
        return database.collection(User.collectionName)
    }

    fun createUser(user: User, onCompleteListener: OnCompleteListener<Void>) {
        val documentRef = getUsersCollection().document(user.id ?: "")
        documentRef.set(user).addOnCompleteListener(onCompleteListener)
    }

    fun getUser(userId: String?,onCompleteListener:OnCompleteListener<DocumentSnapshot>) {
        getUsersCollection().document(userId?:"")
            .get().addOnCompleteListener(onCompleteListener)

    }
}