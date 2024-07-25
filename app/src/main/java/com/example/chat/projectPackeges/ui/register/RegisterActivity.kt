package com.example.chat.projectPackeges.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.chat.R
import com.example.chat.databinding.ActivityRegisterBinding
import com.example.chat.projectPackeges.ui.OnDialogActionClick
import com.example.chat.projectPackeges.ui.home.HomeActivity
import com.example.chat.projectPackeges.ui.login.LoginActivity
import com.example.chat.projectPackeges.ui.showMessage
import com.google.firebase.FirebaseApp

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBinding:ActivityRegisterBinding
    private  lateinit var viewModel : RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        observeData()
    }
    private fun navigateToLogin() {
        val intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToHome() {
        val intent= Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun observeData() {
        viewModel.messageLiveData.observe(this ){
            showMessage(message = it,
                posActionName = "OK"
            )
        }

        viewModel.event.observe(this,::handelEvents)
    }
    private fun handelEvents(registerViewEvent: RegisterViewEvent?) {
        when(registerViewEvent){
            RegisterViewEvent.NavigateToHome ->{
                navigateToHome()
            }
            RegisterViewEvent.NavigateToLogin->{
                navigateToLogin()
            }
            else ->{}
        }
    }

    private fun initViews() {
       // FirebaseApp.initializeApp(this)

        viewBinding=DataBindingUtil.setContentView(this,R.layout.activity_register)
        viewModel=ViewModelProvider(this)[RegisterViewModel::class.java]
        viewBinding.lifecycleOwner=this
        viewBinding.vm=viewModel
    }
}