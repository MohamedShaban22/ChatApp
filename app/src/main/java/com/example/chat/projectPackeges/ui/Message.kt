package com.example.chat.projectPackeges.ui

data class Message(
    val message: String? = null,
    val posActionName: String? = null,
    val onPosActionClick: OnDialogActionClick? = null,
    val onNegActionName: String? = null,
    val negActionClick: OnDialogActionClick? = null,
    val isCancelableAction: Boolean = true

)

fun interface OnDialogActionClick {
    fun onDialogActionClick()
}