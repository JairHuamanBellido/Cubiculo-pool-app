package com.example.cubipool

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.storage.StorageManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.auth.AuthApiService
import com.example.cubipool.service.auth.AuthRequest
import com.example.cubipool.service.auth.AuthResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
    val sharedPreferences = getSharedPreferences("db_local", 0)
        super.onCreate(savedInstanceState)

        if(sharedPreferences.contains("code")){
            val intent = Intent(getApplicationContext(),HomeActivity::class.java)
            startActivity(intent);
        }
        else {


            setContentView(R.layout.activity_main)

            val registerButton = findViewById<TextView>(R.id.register_button)
            val loginButton: Button = findViewById<Button>(R.id.loginButton)
            loginButton.setOnClickListener { auth() }
            registerButton.setOnClickListener { navigateToRegister() }
        }

    }


    private fun auth(){


        val service =  ApiGateway().api.create<AuthApiService>(AuthApiService::class.java)
        val username =  findViewById<EditText>(R.id.codeField).text
        val password =  findViewById<EditText>(R.id.passwordField).text
        val authRequest =  AuthRequest(username.toString()  ,password.toString() )

        service.authenticate(authRequest).enqueue(object : Callback<AuthResponse>{
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                println("Algo salio aml");
                println(t);
            }

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                if (response.isSuccessful()){


                    println("Algo salio bien")
                    val res =  response?.body()
                    val jsonString =  Gson().toJson(res)

                    val _res:AuthResponse = Gson().fromJson(jsonString, AuthResponse::class.java)

                    val intent =  Intent(getApplicationContext(),HomeActivity::class.java);
                    startActivity(intent)
                    saveData(_res.code);
                    finish()
                }
                else{
                    when(response.code()){
                            404 -> Toast.makeText(applicationContext,"Credenciales invalidas",Toast.LENGTH_SHORT).show()
                    }
                }

            }

        })
    }
    private fun navigateToRegister(){


       val intent = Intent(getApplicationContext(),RegisterActivity::class.java)
        startActivity(intent);
    }


    private fun saveData(code:String){
        val editor:SharedPreferences.Editor = getSharedPreferences("db_local",0).edit()
        editor.putString("code",code)

        editor.apply()

    }


}
