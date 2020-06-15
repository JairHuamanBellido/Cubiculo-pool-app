package com.example.cubipool.service.offers

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface OfferService {


    @POST("offers")
    fun createOfferReservation(@Body createOfferReservation: CreateOfferReservation):Call<Any>

    @GET("offers")
    fun findAllOffers():Call<ArrayList<CreateOfferResponse>>

    @GET("offers/{id}/reservation")
    fun findById(@Path("id") id:Int):Call<CreateOfferReservation>

    @GET("offers/{id}")
    fun findByIdOffer(@Path("id") id:Int): Call<CreateOfferReservation>

    @PUT("offers/{id}")
    fun updateOffer(@Path("id") id:Int,@Body offer:CreateOfferReservation):Call<Any>

    @DELETE("offers/{id}")
    fun delete(@Path("id") id:Int):Call<Any>

}

class CreateOfferReservation(
    var reservaId:Int,
    var apple:Boolean,
    var pizarra:Boolean,
    var sitios:Int
)

class CreateOfferResponse(
    var cubiculoNombre:String,
    var horaInicio: String,
    var horaFin: String,
    var apple: Boolean,
    var pizarra: Boolean,
    var sitios: Int,
    var tema: String
)