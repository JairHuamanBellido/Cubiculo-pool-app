package com.example.cubipool

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Adapter.ParticipantsReservationAdapter
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.reservation.ReservationDetail
import com.example.cubipool.service.reservation.ReservationService
import kotlinx.android.synthetic.main.activity_reservation_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationDetailActivity : AppCompatActivity() {

    lateinit var rvParticipants:RecyclerView
    var reservationSevice =  ApiGateway().api.create<ReservationService>(ReservationService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_detail)
        val context =  this
        var id =  intent.getStringExtra("idReservation");
        rvParticipants =  findViewById(R.id.rv_participantsReservations)
        setUpRecycleView()

        getReservation(id.toInt(),context)

    }

    private fun setUpRecycleView(){
        rvParticipants.layoutManager =  LinearLayoutManager(this)
    }


    private fun getReservation(id:Int, context:Activity){

        reservationSevice.findById(id).enqueue(object:Callback<ReservationDetail>{
            override fun onFailure(call: Call<ReservationDetail>, t: Throwable) {
                Log.d("Error Reserva", "Hubo un error al obtener el detalle de la reserva")
            }

            override fun onResponse(
                call: Call<ReservationDetail>,
                response: Response<ReservationDetail>
            ) {


                rvParticipants.adapter =  ParticipantsReservationAdapter(response.body()!!.participantes,context)
                tv_rd_cubiclename.text =  response.body()!!.cubiculoNombre
                tv_rd_startime.text =  response.body()!!.horaInicio
                tv_rd_endtime.text =  response.body()!!.horaFin
                tv_rd_theme.text =  response.body()!!.tema

                Log.d("Primer participante",response.body()!!.participantes.size.toString())
            }
        })
    }
}
