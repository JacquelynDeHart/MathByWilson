package com.example.mathapp

import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

class SplashScreen : AppCompatActivity() {

    lateinit var mHandler: Handler
    lateinit var mRunnable: Runnable

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
        setContentView(R.layout.activity_splash_screen)

        val image = findViewById<ImageView>(R.id.imageView)
        val text = findViewById<TextView>(R.id.textView)

        image.animation.run { R.anim.top_animation }
        text.animation.run { R.anim.bottom_animation }

        startLogin()

    }
    private fun startLogin(){
        mRunnable = Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        mHandler = Handler()
        mHandler.postDelayed(mRunnable, 3000)
    }
    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(mRunnable)
    }
}