package com.example.cubipool.service.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService{
    @POST("auth")
    fun authenticate(@Body authRequest:AuthRequest): Call<AuthResponse>
}


class AuthRequest(var username:String, var password:String)

class AuthResponse(var name:String,var lastName:String, var code:String)