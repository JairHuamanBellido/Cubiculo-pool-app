package com.example.cubipool.service.user

import retrofit2.Call
import retrofit2.http.*

interface UserApiService{

    @POST("users")
    fun register(@Body userRequest:UserRequest)
            :Call<UserRequest>
    @GET("users/{id}")
    fun findById(@Path("id") id:String)
            :Call<UserResponse>

    @GET("users/{id}/reservations?availables=true")
    fun getReservationsAvailables(@Path("id") id:String)
            :Call<ArrayList<UserReservationsAvailables>>

    @GET("users/{id}/hoursAvailable")
    fun getHoursAvailablesByDay(@Path("id")id:String, @Query("date") date:String)
            :Call<UserHoursAvailables>

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

class UserHoursAvailables(
    var horasDisponibles: Int
)

class UserResponse(
    var codigo: String,
    var nombres: String,
    var apellidos: String
)