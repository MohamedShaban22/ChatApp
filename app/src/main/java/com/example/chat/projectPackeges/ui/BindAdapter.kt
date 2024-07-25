package com.example.chat.projectPackeges.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
@BindingAdapter("app:error")
fun bindErrorInTextInputLayout(
    textInputLayout:TextInputLayout,
     errorMessage :String
){
    textInputLayout.error=errorMessage
}