package com.example.cubipool.service.user

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService{

    @POST("users")
    fun register(@Body userRequest:UserRequest): Call<UserRequest>

    @GET("users/{id}/reservations?availables=true")
    fun getReservationsAvailables(@Path("id") id:String):Call<ArrayList<UserReservationsAvailables>>


}



class UserRequest(var code:String, var name:String, var lastName:String, var password:String)

class UserReservationsAvailables(
    var id: Int,
    var name: String,
    var startTime: String,
    var endTime: String,
    var day: String,
    var status: String
)