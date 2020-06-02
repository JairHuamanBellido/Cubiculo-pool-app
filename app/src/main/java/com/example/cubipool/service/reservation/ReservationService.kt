package com.example.cubipool.service.reservation

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ReservationService {
    @POST("reservation")
    fun submitReservation(@Body reservationRequest: ReservationRequest): Call<ReservationRequest>

    @GET("reservation/{id}/{code}")
    fun findById(@Path("id") id: Int, @Path("code") code: String): Call<ReservationDetail>

    @PUT("reservation/activate")
    fun activateCubicle(@Body activateReservation: ActivateReservation): Call<ActivateReservation>
}

class ActivateReservation(
    var codigo: String,
    var reservaId:Int
)

class ReservationRequest(
    var fecha:String,
    var hora_inicio:String,
    var hora_fin:String,
    var sede:String,
    var codigo_uno:String,
    var codigo_dos:String,
    var cubiculo_id:Int,
    var theme:String
)


class ReservationDetail(
    var cubiculoNombre: String,
    var horaInicio: String,
    var horaFin: String,
    var tema: String,
    var estado: String,
    var sitiosDisponible: Int,
    var sede: String,
    var participantes: ArrayList<ParticipantsReservation>,
    var activate:String,
    var offer:ArrayList<Offer>,
    var rol:String
)

class ParticipantsReservation(
    var codigo:String,
    var nombre:String
)


class Offer(
    var id:Int,
    var apple:Boolean,
    var pizarra: Boolean,
    var sitios:Int,
    var disponible: Boolean
)