package com.example.mathapp

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
=======
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_testing.*
>>>>>>> origin/master

class Testing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)
<<<<<<< HEAD
=======

        video_button.setOnClickListener{
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
>>>>>>> origin/master
    }
}