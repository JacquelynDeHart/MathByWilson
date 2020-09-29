package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_college_algebra.*

class Calculus2 : AppCompatActivity() {
    private var COURSE_SELECT = 0
    private val ACTIVITY_NUM = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculus2)

        eqs_lin.setOnClickListener {
            COURSE_SELECT = 1
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
    }
}