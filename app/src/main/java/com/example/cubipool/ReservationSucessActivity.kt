package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reservation_sucess.*

class ReservationSucessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_sucess)
        btnGotToHome.setOnClickListener { goToHome() }

    }

    /**
     * Regresa al home
     */
    private fun goToHome(){
        val intent =  Intent(applicationContext,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
