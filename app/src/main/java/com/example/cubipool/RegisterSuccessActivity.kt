package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_success)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener{goToLogin()}
    }

    private fun goToLogin(){
        val intent =  Intent(getApplicationContext(),LoginActivity::class.java)
        startActivity(intent);
       finish()
    }
}
