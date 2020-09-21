package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

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

        sign_in.setOnClickListener {
//            Toast.makeText(this, "Function coming soon" , Toast.LENGTH_SHORT).show()
            doLogin()
        }
        forgot_pass.setOnClickListener {
            Toast.makeText(this, "Function coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                startActivity(Intent(this, Testing::class.java))
            }else{
                // If sign in fails, display a message to the user.
                Toast.makeText(baseContext, "Please verify your email.",
                    Toast.LENGTH_SHORT).show()
            }

        }else{
            // If sign in fails, display a message to the user.
            Toast.makeText(baseContext, "Please enter your credentials.",
                Toast.LENGTH_SHORT).show()
        }
    }

    fun doLogin(){
        if(!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()){
            username.error= "Please enter an valid email address"
            username.requestFocus()
            return
        }
        if(password.text.toString().isEmpty()){
            first_pass.error= "Please enter a password"
            first_pass.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }
}