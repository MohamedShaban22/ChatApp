package com.example.chat.projectPackeges.ui.chat_packege

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.chat.R
import com.example.chat.databinding.ActivityChatBinding
import com.example.chat.projectPackeges.model.Room
import com.example.chat.projectPackeges.ui.Constants

class ChatActivity : AppCompatActivity() {
     val viewModel:ChatViewModel by viewModels()
    lateinit var viewBinding :ActivityChatBinding
    lateinit var adapter:MessageAdapter
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter= MessageAdapter(mutableListOf())
        initViews()
        initParameters()
        observeData()
    }


    private fun observeData() {
        viewModel.toastMessage.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
        viewModel.newMessagesLiveData.observe(this){
            adapter.addNewMessages(it)
            if (adapter.itemCount>0){
                viewBinding.content.messagesRecycler
                    .smoothScrollToPosition(adapter.itemCount-1)
            }

        }
        viewModel.roomNameLiveData.observe(this){
            viewBinding.roomNameTv.text=it.toString()
        }
    }

    private fun initParameters() {
        val room =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(Constants.EXTRA_ROOM, Room::class.java)
            }
            else{
                intent.getParcelableExtra(Constants.EXTRA_ROOM) as Room?
            }
        viewModel.changeRoom(room)
    }

    private fun initViews() {
        viewBinding=DataBindingUtil.setContentView(this,R.layout.activity_chat)
        viewBinding.lifecycleOwner=this
        viewBinding.vm=viewModel
        viewBinding.content.messagesRecycler.adapter=adapter
        if (adapter.itemCount >0){
            viewBinding.content.messagesRecycler.scrollToPosition(adapter.itemCount-1)
        }

        viewBinding.icBack.setOnClickListener {
            finish()
        }

    }
}