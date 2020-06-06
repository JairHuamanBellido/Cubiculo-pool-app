package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.user.UserApiService
import com.example.cubipool.service.user.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton:Button=  findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener{register()}
    }


    private fun register(){
        val code =  findViewById<EditText>(R.id.codeField).text.toString()
        val name  =  findViewById<EditText>(R.id.nameField).text.toString()
        val lastName =  findViewById<EditText>(R.id.lastnameField).text.toString()
        val password =  findViewById<EditText>(R.id.passwordField).text.toString()

        val userRequest =  UserRequest(code,name,lastName,password)

        val userService =  ApiGateway().api.create<UserApiService>(UserApiService::class.java)


        userService.register(userRequest).enqueue(object : Callback<UserRequest>{
            override fun onFailure(call: Call<UserRequest>, t: Throwable) {
                Log.e("error", "Hubo un error al momento de registrarsse")
            }

            override fun onResponse(call: Call<UserRequest>, response: Response<UserRequest>) {
                if(response.isSuccessful()){
                    Log.e("registro exitoso", "bienvenido")
                    val intent = Intent(getApplicationContext(),RegisterSuccessActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                else{
                    when(response.code()){
                        409 -> Toast.makeText(applicationContext,"El código ya esta registrado",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


    }
}
