package com.example.githubuser.application.appsplashscreen
import com.example.githubuser.databinding.UserSplashscreenBinding
import com.example.githubuser.application.mainactivity.MainActivity
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle



@SuppressLint("CustomSplashScreen")
class ApplicationSplashScreen : AppCompatActivity() {
    private lateinit var _isbinding: UserSplashscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _isbinding = UserSplashscreenBinding.inflate(layoutInflater)
        setContentView(_isbinding.root)
        supportActionBar?.hide()
        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 2200L) } }