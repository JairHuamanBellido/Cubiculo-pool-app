package com.example.cubipool.service.offers

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OfferService {


    @POST("offers")
    fun createOfferReservation(@Body createOfferReservation: CreateOfferReservation):Call<Any>

    @GET("offers/{id}/reservation")
    fun findById(@Path("id") id:Int)
}

class CreateOfferReservation(
    var reservaId:Int,
    var apple:Boolean,
    var pizarra:Boolean,
    var sitios:Int
)