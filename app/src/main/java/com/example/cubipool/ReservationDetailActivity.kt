package com.example.cubipool

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Adapter.ParticipantsReservationAdapter
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.reservation.ActivateReservation
import com.example.cubipool.service.reservation.Offer
import com.example.cubipool.service.reservation.ReservationDetail
import com.example.cubipool.service.reservation.ReservationService
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_reservation_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationDetailActivity : AppCompatActivity() {

    lateinit var rvParticipants:RecyclerView
    lateinit var codigo:String
    lateinit var idReserva: String;
    lateinit var id:String;
    var reservationSevice =  ApiGateway().api.create<ReservationService>(ReservationService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_detail)
        val context =  this
        id =  intent.getStringExtra("idReservation");

        rvParticipants =  findViewById(R.id.rv_participantsReservations)
        setUpRecycleView()

        codigo = getSharedPreferences("db_local",0).getString("code",null);

        btn_ActivateReservation.setOnClickListener { activateReservation(codigo, id.toInt()) }
        btnSharedCubicleActivity.setOnClickListener { goToShareCubicleActivity() }

        ll_offer.visibility = View.GONE
        ll_noOffer.visibility =  View.GONE;
        ll_offerAppleTV.visibility =  View.GONE
        ll_offerBoard.visibility =  View.GONE
        ll_porActivar.visibility =  View.GONE
    }

    override fun onResume() {
        super.onResume()
        idReserva = id;
        getReservation(id.toInt(),codigo,this)

    }

    private fun setUpRecycleView(){
        rvParticipants.layoutManager =  LinearLayoutManager(this)
    }


    private fun getReservation(id:Int, code:String,context:Activity){

        reservationSevice.findById(id,code).enqueue(object:Callback<ReservationDetail>{
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

                if(response.body()!!.offer.size > 0 && response.body()!!.estado == "Activado" && response.body()!!.rol == "Admin"){
                    showOffer(response.body()!!.offer[0])
                }
                else if(response.body()!!.offer.size < 1 && response.body()!!.estado == "Activado" && response.body()!!.rol == "Admin"){
                    ll_noOffer.visibility = View.VISIBLE
                }

                if(response.body()!!.activate == "false" && (response.body()!!.estado == "PorActivar" || response.body()!!.estado == "Activado1")){
                    ll_porActivar.visibility = View.VISIBLE
                }
                else if(response.body()!!.activate == "true"){
                    ll_porActivar.visibility =  View.GONE
                }
                Log.d("Por activar",response.body()!!.activate.toString())
            }
        })
    }

        private fun activateReservation(codigo:String,reservaId:Int){
        reservationSevice.activateCubicle(ActivateReservation(codigo,reservaId)).enqueue(object:Callback<ActivateReservation>{
            override fun onFailure(call: Call<ActivateReservation>, t: Throwable) {
                    Log.d("Error","Hubo un error en la activacion")
            }

            override fun onResponse(call: Call<ActivateReservation>, response: Response<ActivateReservation>) {
                    ll_porActivar.visibility =  View.GONE
            }
        })


    }

    private fun goToShareCubicleActivity(){
        val intent  = Intent(this,ShareCubicleActivity::class.java)
        intent.putExtra("reservaId", this.idReserva)
        startActivity(intent)
    }

    private fun showOffer(offer:Offer){
        ll_offer.visibility = View.VISIBLE;

        if(offer.apple){
            ll_offerAppleTV.visibility =  View.VISIBLE
        }
        if(offer.pizarra){
            ll_offerBoard.visibility = View.VISIBLE
        }
        tv_rd_asientosOffer.text  =  offer.sitios.toString() + " asientos"
    }
}
