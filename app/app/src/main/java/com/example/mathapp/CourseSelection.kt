package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_course_selection.*


class CourseSelection : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_selection)

        course_colAlg.setOnClickListener {
            startActivity(Intent(this, CollegeAlgebra::class.java))

        }
        course_cal1.setOnClickListener {
            startActivity(Intent(this, Calculus1::class.java))

        }
        course_cal2.setOnClickListener {
            startActivity(Intent(this, Calculus2::class.java))

        }
    }

}

/*
var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

tabLayout= findViewById(R.id.tabLayout)
viewPager= findViewById(R.id.viewPager)

val colAlg: String = getString(R.string.college_algebra)
val cal1: String = getString(R.string.calc_1)
val cal2: String = getString(R.string.calc_2)

tabLayout!!.addTab(tabLayout!!.newTab().setText(colAlg))
tabLayout!!.addTab(tabLayout!!.newTab().setText(cal1))
tabLayout!!.addTab(tabLayout!!.newTab().setText(cal2))
tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

val adapter = MyAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
viewPager!!.adapter = adapter

viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
        viewPager!!.currentItem = tab.position
    }
    override fun onTabUnselected(tab: TabLayout.Tab) {

    }
    override fun onTabReselected(tab: TabLayout.Tab) {

    }
})*/
