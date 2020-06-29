package com.example.cubipool.service.offers

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface OfferService {


    @POST("offers")
    fun createOfferReservation(@Body createOfferReservation: CreateOfferReservation):Call<Any>

    @POST("offers/invitation")
    fun joinOffer(@Body createJoinOffer: CreateJoinOffer):Call<JSONObject>

    @GET("offers")
    fun findAllOffers():Call<ArrayList<CreateOfferResponse>>

    @GET("offers/{id}/reservation")
    fun findById(@Path("id") id:Int):Call<CreateOfferReservation>

    @GET("offers/{id}")
    fun findByIdOffer(@Path("id") id:Int): Call<OfferDetailJoin>

    @PUT("offers/{id}")
    fun updateOffer(@Path("id") id:Int,@Body offer:UpdateOfferModel):Call<Any>

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
    var tema: String,
    var offerId:Int
)

class OfferDetailJoin(
    var appleTv:Boolean,
    var asientos:Int,
    var pizarra:Boolean,
    var cubiculoNombre: String
)

class CreateJoinOffer(
    var codigo:String,
    var apple: Boolean,
    var pizarra: Boolean,
    var ofertaId: Int
)

class UpdateOfferModel(
    var apple:Boolean,
    var pizarra: Boolean,
    var sitios: Int
)