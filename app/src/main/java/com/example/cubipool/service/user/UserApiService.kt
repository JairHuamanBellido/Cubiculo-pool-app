package com.example.cubipool.service.user

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService{
    @POST("users")
    fun register(@Body userRequest:UserRequest): Call<UserRequest>
}

class UserRequest(var code:String, var name:String, var lastName:String, var password:String)

