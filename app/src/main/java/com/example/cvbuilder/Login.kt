package com.example.cvbuilder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cvbuilder.helpers.Validators
import com.example.cvbuilder.models.User

class Login : AppCompatActivity() {
    var mockedUser = User("felipe", "zeba", "felipe@gmail.com", "felipe123")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        login_btn_login.setOnClickListener {
            var email = login_input_email.text.toString()
            var password = login_input_password.text.toString()

            var loginResult = login(email, password)
            if(loginResult) {
                clearInputs()
                var intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("user", "")
                startActivity(intent)
            }
        }
        */
    }

    private fun login(email: String?, password: String?): Boolean {
        if(email == "" || email == null || password == "" || password == null){
            Toast.makeText(applicationContext, "Both fields are required", Toast.LENGTH_LONG)
            return false
        }
        if(!Validators.isValidEmail(email)) {
            Toast.makeText(applicationContext, "E-mail format is invalid", Toast.LENGTH_LONG)
            return false
        }

        var returneduser = findUserByEmail(email)
        return if(returneduser?.password == password) {
            // SAVE USER TO SHARED PREFERENCES
            true
        } else {
            Toast.makeText(applicationContext, "Invalid user", Toast.LENGTH_LONG)
            false
        }

    }

    private fun findUserByEmail(email: String): User? {
        // FIND USER IN THE DATABASE
        return mockedUser
    }

    private fun clearInputs() {
        //login_input_email.text.clear()
        //login_input_password.text.clear()
    }
}