package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_calculus1.*
import kotlinx.android.synthetic.main.activity_college_algebra.*

class Calculus1 : AppCompatActivity() {
    private var COURSE_SELECT = 0
    private val ACTIVITY_NUM = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculus1)

        fun_lim.setOnClickListener {
            COURSE_SELECT = 1
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        fun_graph.setOnClickListener {
            COURSE_SELECT = 2
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        fin_2.setOnClickListener {
            COURSE_SELECT = 3
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        fin_1.setOnClickListener {
            COURSE_SELECT = 4
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        con_func.setOnClickListener {
            COURSE_SELECT = 5
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        infinite.setOnClickListener {
            COURSE_SELECT = 6
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        to_infin.setOnClickListener {
            COURSE_SELECT = 7
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        to_infin_in.setOnClickListener {
            COURSE_SELECT = 8
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        derv_intro.setOnClickListener {
            COURSE_SELECT = 9
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        derv_spec.setOnClickListener {
            COURSE_SELECT = 10
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        derv_gen_1.setOnClickListener {
            COURSE_SELECT = 11
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        derv_gen_2.setOnClickListener {
            COURSE_SELECT = 12
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
        derv_rev.setOnClickListener {
            COURSE_SELECT = 13
            val intent = Intent(this, VideoUrl::class.java)
            intent.putExtra("urlCode", COURSE_SELECT)
            intent.putExtra("activity", ACTIVITY_NUM)
            startActivity(intent)
        }
    }
}