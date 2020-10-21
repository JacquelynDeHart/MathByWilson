package com.example.mathapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_course_selection.*


class CourseSelection : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_selection)
        setSupportActionBar(findViewById(R.id.tbar))

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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
        when (item.itemId) {
            R.id.action_settings -> {
//                val intent = Intent(this, SettingsMain::class.java)
//                startActivity(intent)
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                Toast.makeText(this, "will take to settings for app later", Toast.LENGTH_SHORT).show()
            }
            R.id.action_logout ->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.action_about ->{
                Toast.makeText(this, "An application for mathbywilson.com", Toast.LENGTH_LONG).show()
            }
            R.id.action_email ->{
                Toast.makeText(this, "send email to professor. function coming soon", Toast.LENGTH_SHORT).show()
            }
            R.id.action_exit -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)

}}

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
