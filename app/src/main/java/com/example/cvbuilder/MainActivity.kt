package com.example.cvbuilder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cvbuilder.helpers.Validators
import com.example.cvbuilder.models.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fakeDatabase = ArrayList<User>(arrayListOf(
        User("felipe", "zeba", "felipe@gmail.com", "felipe123")
    ))
    private lateinit var sharedPreference: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * INIT SHARED PREFERENCES VARIABLE
         */
        sharedPreference =  getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)

        /*
         * private
         * CLICK EVENT FOR THE LOGIN BUTTON
         * @returns Unit
         */
        login_btn_login.setOnClickListener {
            var email = login_email.text.toString()
            var password = login_password.text.toString()

            var loginResult = login(email, password)
            if(loginResult) {
                clearInputs()
                goToHomeScreen()
            }
        }

        //VERIFY IF USER IS SAVED AND, IF SO, LOG IN
        retrieveLogin()
    }

    private fun goToHomeScreen() {
        var intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    /*
     * private
     * RETRIEVE THE EMAIL AND PASSWORD STORED IN SHARED PREFERENCES IN USE THEM TO LOGIN
     * @returns Unit
     */
    private fun retrieveLogin() {
        val emailShared = sharedPreference.getString("email", "")
        val passwordShared = sharedPreference.getString("password", "")
        if(emailShared != "" && passwordShared != "") {
            val loginResult = login(emailShared, passwordShared)
            if(loginResult) {
                clearInputs()
                goToHomeScreen()
            }
        }
    }

    /*
     * private
     * SIGN THE USER AND SAVE THE EMAIL AND PASSWORD INTO SHARED PREFERENCES
     * @params email: String - the user's email
     * @params password: String - the user's password
     * @returns Unit
     */
    private fun login(email: String?, password: String?): Boolean {
        if(email == "" || email == null || password == "" || password == null){
            Toast.makeText(applicationContext, "Both fields are required", Toast.LENGTH_LONG).show()
            return false
        }
        if(!Validators.isValidEmail(email)) {
            Toast.makeText(applicationContext, "E-mail format is invalid", Toast.LENGTH_LONG).show()
            return false
        }

        var returneduser = findUserByEmail(email)
        return if(returneduser?.password == password) {
            // SAVE USER TO SHARED PREFERENCES
            val editor = sharedPreference.edit()
            editor.put(Pair("email", email))
            editor.put(Pair("password", password))
            editor.put(Pair("username", returneduser.firstname + " " + returneduser.lastname))
            editor.apply()
            true
        } else {
            Toast.makeText(applicationContext, "Invalid user", Toast.LENGTH_LONG).show()
            false
        }

    }


    /*
     * private
     * SEARCHES THE USER IN THE DATABASE BY EMAIL
     * @params email: String - the user's email
     * @returns User
     */
    private fun findUserByEmail(email: String): User? {
        // FIND USER IN THE DATABASE
        var user: User? = null
        fakeDatabase.forEach {
            if(it.email == email) {
                user = it
            }
        }
        return user
    }

    /*
     * private
     * CLEARS THE FORM'S INPUTS
     * @returns Unit
     */
    private fun clearInputs() {
        login_email.text.clear()
        login_password.text.clear()
    }

    /*
     * public
     * EXTENSION TO SharedPreferences CLASS TO SIMPLIFY THE SAVIN PROCESS
     * @returns Unit
     */
    fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
        val key = pair.first
        val value = pair.second
        when(value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }
}
