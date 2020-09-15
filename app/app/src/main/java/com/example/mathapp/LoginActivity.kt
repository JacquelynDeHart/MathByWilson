package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
        val user: Editable? = username.text
        val pass: Editable? = password.text

        sign_in.setOnClickListener {
            Toast.makeText(this, "Function coming soon" , Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?){

    }
}