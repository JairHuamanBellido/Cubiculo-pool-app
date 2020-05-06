package com.example.cubipool

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.storage.StorageManager
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView

import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.auth.AuthApiService
import com.example.cubipool.service.auth.AuthRequest
import com.example.cubipool.service.auth.AuthResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {


    lateinit var loginButton:Button;
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
             loginButton= findViewById<Button>(R.id.loginButton)
            val loadingButton =  findViewById<LottieAnimationView>(R.id.loadingANimation)
            loginButton.setOnClickListener { auth() }
            registerButton.setOnClickListener { navigateToRegister() }
        }

    }


    private fun auth(){


        val service =  ApiGateway().api.create<AuthApiService>(AuthApiService::class.java)
        val username =  findViewById<EditText>(R.id.codeField).text
        val password =  findViewById<EditText>(R.id.passwordField).text
        val authRequest =  AuthRequest(username.toString()  ,password.toString() )
        loadingANimation.playAnimation()
        loadingANimation.visibility =View.VISIBLE
        loginButton.visibility =  View.GONE
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
                    saveData(_res.code,_res.name,_res.lastName);
                    finish()
                }
                else{
                    when(response.code()){
                            404 -> {
                                Toast.makeText(applicationContext,"Credenciales invalidas",Toast.LENGTH_SHORT).show()
                                loadingANimation.visibility =View.GONE

                                loginButton.visibility =  View.VISIBLE
                            }
                    }
                }

            }

        })
    }
    private fun navigateToRegister(){


       val intent = Intent(getApplicationContext(),RegisterActivity::class.java)
        startActivity(intent);
    }


    private fun saveData(code:String, name:String,lastName:String){
        val editor:SharedPreferences.Editor = getSharedPreferences("db_local",0).edit()
        editor.putString("code",code)
        editor.putString("nombre", name)
        editor.putString("lastName", lastName)

        editor.apply()

    }


}
