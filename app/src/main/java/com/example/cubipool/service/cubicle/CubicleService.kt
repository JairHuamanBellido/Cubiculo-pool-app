package com.example.cubipool.service.cubicle

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface CubicleService{
    @GET("cubicles")
    fun findAllAvailableCubicles(@Query("date") date:String, @Query("startTime") startTime:String,@Query("hours") hours:String): Call<ArrayList<CubicleResponse>>
}




class CubicleResponse(var id:Int,var name:String, var startTime: String, var endTime:String, var day:String, var status:Boolean =  false)