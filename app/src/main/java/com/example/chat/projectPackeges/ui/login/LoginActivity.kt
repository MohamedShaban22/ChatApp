package com.example.chat.projectPackeges.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chat.R
import com.example.chat.databinding.ActivityLoginBinding
import com.example.chat.projectPackeges.ui.OnDialogActionClick
import com.example.chat.projectPackeges.ui.home.HomeActivity
import com.example.chat.projectPackeges.ui.register.RegisterActivity
import com.example.chat.projectPackeges.ui.register.RegisterViewEvent
import com.example.chat.projectPackeges.ui.showMessage


class LoginActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityLoginBinding
    private  lateinit var viewModel : LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        observeData()

    }

    private fun observeData() {
        viewModel.messageLiveData.observe(this){
            showMessage(message = it
            ,posActionName = "OK"
            )
        }
        viewModel.event.observe(this,::handelEvents)
    }

    private fun handelEvents(registerViewEvent: LoginViewEvent?) {
        when(registerViewEvent){
            LoginViewEvent.NavigateToHome ->{
                navigateToHome()
            }
            LoginViewEvent.NavigateToRegister->{
                navigateToRegister()
            }
            else ->{}
        }
    }
    private fun navigateToRegister() {
        val intent= Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToHome() {
        val intent= Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        viewBinding= DataBindingUtil.setContentView(this,R.layout.activity_login)
        viewModel= ViewModelProvider(this)[LoginViewModel::class.java]
        viewBinding.lifecycleOwner=this
        viewBinding.vm=viewModel
    }
}