package com.example.chat.projectPackeges.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.DialogInterface

//fun Activity.showMessage(
//    message: String,
//    posActionName: String? = null,
//    posAction: OnDialogActionClick? = null,
//    negActionName: String? = null,
//    negAction: OnDialogActionClick? = null,
//    isCancelable: Boolean? = true
//
//): AlertDialog {
//    val dialogBuilder = AlertDialog.Builder(this)
//    dialogBuilder.setMessage(message)
//    if (posActionName != null) {
//        dialogBuilder.setPositiveButton(posActionName) { dialog, id ->
//            dialog.dismiss()
//            posAction?.onDialogActionClick()
//        }
//    }
//    if (negActionName != null) {
//        dialogBuilder.setNegativeButton(negActionName) { dialog, id ->
//            dialog.dismiss()
//            negAction?.onDialogActionClick()
//        }
//
//    }
//    dialogBuilder.setCancelable(isCancelable ?: true)
//
//    return dialogBuilder.show()
//}
fun Activity.showMessage(message:String
                                        , posActionName:String?=null
                                        , posAction: DialogInterface.OnClickListener?=null
                                        , negActionName:String?=null
                                        , negAction: DialogInterface.OnClickListener?=null

): AlertDialog {
    val dialogBuilder= AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
    if (posActionName!=null)
    {
        dialogBuilder.setPositiveButton(posActionName,posAction)
    }
    if(negActionName!=null)
    {
        dialogBuilder.setNegativeButton(negActionName,negAction)

    }
    return dialogBuilder.show()
}


fun Fragment.showMessage(message:String
                                        , posActionName:String?=null
                                        , posAction: DialogInterface.OnClickListener?=null
                                        , negActionName:String?=null
                                        , negAction: DialogInterface.OnClickListener?=null

): AlertDialog {
    val dialogBuilder= AlertDialog.Builder(context)
    dialogBuilder.setMessage(message)
    if (posActionName!=null)
    {
        dialogBuilder.setPositiveButton(posActionName,posAction)
    }
    if(negActionName!=null)
    {
        dialogBuilder.setNegativeButton(negActionName,negAction)

    }
    return dialogBuilder.show()
}