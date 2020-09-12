package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        val user: Editable? = username.text
        val pass: Editable? = password.text

        sign_in.setOnClickListener {
            Toast.makeText(this, "Function coming soon" , Toast.LENGTH_SHORT).show()
        }
    }
}