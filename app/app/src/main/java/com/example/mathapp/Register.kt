package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        add_user.setOnClickListener {
            //push stuff to the firebase database after validating input
            checkUserInput()
        }
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
    private fun passCheck(a: String, b:String){
        if(!a.equals(b)){
            Toast.makeText(this, "password not the same", Toast.LENGTH_SHORT).show()
            first_pass.requestFocus()
        }
        else{
            Toast.makeText(this,"Passwords Match", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkUserInput(){
        if(first_name.text.toString().isEmpty()){
            first_name.error="Please enter your first name"
            first_name.requestFocus()
            return
        }
        if(last_name.text.toString().isEmpty()){
            last_name.error= "Please enter your last name"
            last_name.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(new_user.text.toString()).matches()){
            username.error= "Please enter an valid email address"
            username.requestFocus()
            return
        }
        if(first_pass.text.toString().isEmpty()){
            first_pass.error= "Please enter a password"
            first_pass.requestFocus()
            return
        }
        if(second_pass.text.toString().isEmpty()){
            second_pass.error= "Re-enter your password"
            second_pass.requestFocus()
            return
        }
        passCheck(first_pass.text.toString(), second_pass.text.toString())

        auth.createUserWithEmailAndPassword(new_user.text.toString(), first_pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, LoginActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Registration of new user failed. Try again later.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


}