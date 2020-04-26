package com.example.cubipool.service.reservation

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReservationService{
    @POST("reservation")
    fun submitReservation(@Body reservationRequest: ReservationRequest): Call<ReservationRequest>
}

class ReservationRequest(
    var fecha:String,
    var hora_inicio:String,
    var hora_fin:String,
    var sede:String,
    var codigo_uno:String,
    var codigo_dos:String,
    var cubiculo_id:Int
)

