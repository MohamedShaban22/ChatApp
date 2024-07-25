package com.example.chat.projectPackeges.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.chat.R
import com.example.chat.databinding.ActivitySplashBinding
import com.example.chat.projectPackeges.ui.home.HomeActivity
import com.example.chat.projectPackeges.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var viewBinding:ActivitySplashBinding
    val viewModel :SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding=DataBindingUtil.setContentView(this,
            R.layout.activity_splash )

        Handler(Looper.getMainLooper()).postDelayed({
           viewModel.navigate()
        },1000)

    observeData()
    }

    private fun observeData() {
        viewModel.event.observe(this){
            when (it){
                SplashViewEvent.NavigateToLogin->{navigateToLogin()}
                SplashViewEvent.NavigateToHome->{navigateToHome()}
                else ->{}
            }
        }
    }

    private fun navigateToHome() {
    val intent= Intent(this, HomeActivity::class.java)
    startActivity(intent)
    finish()
    }

    private fun navigateToLogin() {
    val intent= Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
    }

//    val intent= Intent(this, LoginActivity::class.java)
//    startActivity(intent)
//    finish()

}