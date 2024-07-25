package com.example.chat.projectPackeges.ui.chat_packege

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chat.databinding.ItemMessageReciveBinding
import com.example.chat.databinding.ItemMessageSentBinding
import com.example.chat.projectPackeges.fireStore.SessionProvider
import com.example.chat.projectPackeges.model.Message

class MessageAdapter(val messagesList:MutableList<Message>):RecyclerView.Adapter<ViewHolder>() {
    companion object{
        const val sent =100
        const val receive=200
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when(viewType){
            sent->{val itemBinding=ItemMessageSentBinding
                .inflate(LayoutInflater.from(parent.context),parent,false)
                 SentMessageViewHolder(itemBinding)
            }
            receive->{val itemBinding=ItemMessageReciveBinding
                .inflate(LayoutInflater.from(parent.context),parent,false)
                 ReceiveMessageViewHolder(itemBinding)
            }

            else -> {  throw IllegalArgumentException("Invalid view type")}
        }

    }

    override fun getItemViewType(position: Int): Int {
         if(messagesList[position].senderId ==SessionProvider.user?.id){
            return sent
        }
        else{return receive}
    }
    override fun getItemCount(): Int {
       return messagesList.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is SentMessageViewHolder){
            holder.bind(messagesList[position])
        }
        else if(holder is ReceiveMessageViewHolder){
            holder.bind(messagesList[position])
        }
    }

    fun addNewMessages(newMessages: List<Message>?) {
        if (newMessages != null) {
            val oldPosition=messagesList.size
            messagesList.addAll(newMessages)
            notifyItemRangeChanged(oldPosition,newMessages.size)
        }
    }
}

class SentMessageViewHolder(val itemBinding:ItemMessageSentBinding):RecyclerView.ViewHolder(itemBinding.root){
    fun bind(message: Message){
        itemBinding.message = message
    }
}
class ReceiveMessageViewHolder(val itemBinding:ItemMessageReciveBinding):RecyclerView.ViewHolder(itemBinding.root){
    fun bind(message: Message){
        itemBinding.message = message
    }
}