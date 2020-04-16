package com.example.cubipool

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var logoutButton:Button =  findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener{logout()}

    }

    private fun logout(){
        val sharedPreferences:SharedPreferences =  getSharedPreferences("db_local",0);
        val editor = sharedPreferences.edit()
        editor.remove("code")
        editor.apply()

        val intent  = Intent(getApplicationContext(),LoginActivity::class.java);
        startActivity(intent);
    }
}
