package com.example.mathapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class CourseSelection : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_selection)

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
        })

    }
}