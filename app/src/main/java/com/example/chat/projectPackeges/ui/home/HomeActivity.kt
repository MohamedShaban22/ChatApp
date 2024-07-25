package com.example.chat.projectPackeges.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chat.R
import com.example.chat.databinding.ActivityHomeBinding
import com.example.chat.projectPackeges.model.Room
import com.example.chat.projectPackeges.ui.Constants
import com.example.chat.projectPackeges.ui.addRoom.AddRoomActivity
import com.example.chat.projectPackeges.ui.chat_packege.ChatActivity
import com.example.chat.projectPackeges.ui.login.LoginActivity
import com.example.chat.projectPackeges.ui.showMessage

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var adapter: RoomsAdapter

    override fun onStart() {
        super.onStart()
        viewModel.getRooms()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        observeData()
        onRoomClick()
        icSearchClickListener()
        setupSearchView()
    }

    private fun icSearchClickListener() {

        viewBinding.icSearch.setOnClickListener{
            viewBinding.chatTv.visibility=View.GONE
            viewBinding.icDrawer.visibility=View.GONE
            viewBinding.searchView.isVisible=true
            viewBinding.icSearch.visibility=View.GONE

        }

        viewBinding.searchView.setOnCloseListener {
            viewBinding.chatTv.visibility=View.VISIBLE
            viewBinding.icDrawer.visibility=View.VISIBLE
            viewBinding.searchView.visibility=View.GONE
            viewBinding.icSearch.visibility=View.VISIBLE
            return@setOnCloseListener true
        }
    }

    private fun onRoomClick() {
        adapter.onItemClickListener =
            RoomsAdapter.OnItemClickListener { position, room ->
                navigateToChatActivity(room!!)
            }
    }

    private fun navigateToChatActivity(room: Room) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM, room)
        startActivity(intent)
    }


    private fun observeData() {
        viewModel.userNameLiveData.observe(this) {
            val header = viewBinding.navigationView.getHeaderView(0)
            val nameTextView = header.findViewById<TextView>(R.id.name_drawer_tv)
            nameTextView.text = "Hi, $it"
        }
        viewModel.event.observe(this) {
            when (it) {
                HomeViewEvent.NavigateToAddRome -> navigateToAddRoom()
                HomeViewEvent.NavigateToLogin -> navigateToLogin()
                else -> {}
            }
        }
        viewModel.filteredRooms.observe(this) {
            adapter.bindRooms(it)
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }

    private fun initViews() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel
        adapter = RoomsAdapter()
        viewBinding.content.roomsRecycler.adapter = adapter
        openDrawer()
        drawerItemsSelected()
        viewModel.userName()
    }

    private fun setupSearchView() {
        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterRooms(newText ?: "")
                return true
            }
        })
    }

    private fun drawerItemsSelected() {
        viewBinding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout_drawer -> {
                    showMessage("Do you Sure To Logout ",
                        posActionName = "OK",
                        posAction = { dialogInterface, i ->
                            dialogInterface.dismiss()
                            viewModel.logout()
                        },
                        negActionName = "Cancel",
                        negAction = { dialogInterface, i ->
                            dialogInterface.dismiss()
                        }
                    )
                }
            }
            true
        }
    }

    private fun openDrawer() {
        viewBinding.icDrawer.setOnClickListener {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}
