package com.example.chat.projectPackeges.ui.addRoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.chat.R
import com.example.chat.databinding.ActivityAddRoomBinding
import com.example.chat.projectPackeges.ui.home.HomeActivity
import com.example.chat.projectPackeges.ui.showMessage

class AddRoomActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityAddRoomBinding
      val viewModel:AddRoomViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        observeData()
    }

    private fun observeData() {

        viewModel.events.observe(this@AddRoomActivity,::handelEvents)
    }

    private fun handelEvents(addRoomViewEvent: AddRoomViewEvent?) {
        when (addRoomViewEvent){
            AddRoomViewEvent.NavigateToHomeAndFinish->{
                finish()
            }
            else -> {}
        }
    }



    lateinit var adapter:AddRoomCategoriesAdapter

    private fun initViews() {
        adapter= AddRoomCategoriesAdapter(CategoriesItem.getCategories())
        viewBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_room)
        viewBinding.lifecycleOwner=this
        viewBinding.vm=viewModel
        viewBinding.content.spinnerCategories.adapter=adapter

        viewBinding.content.spinnerCategories.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onCategorySelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}