package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.offers.CreateOfferReservation
import com.example.cubipool.service.offers.OfferService
import com.example.cubipool.service.reservation.Offer
import kotlinx.android.synthetic.main.activity_share_cubicle.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShareCubicleActivity : AppCompatActivity() {
    lateinit var sitiosDisponibleAdapter:  ArrayAdapter<Int>;
     var reservaId:Int = 0;
     var appleTv:Boolean = false;
     var pizarra:Boolean =  false;
    var sitios:Int =  4;
    val offerService =  ApiGateway().api.create<OfferService>(OfferService::class.java)
    var  sitiosDisponibles: MutableList<Int> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservaId =  intent.getStringExtra("reservaId").toInt();


        Log.d("reserva id", reservaId.toString())
        setContentView(R.layout.activity_share_cubicle)
        sitiosDisponibles.add(1);
        sitiosDisponibles.add(2);
        sitiosDisponibles.add(3);
        sitiosDisponibles.add(4);

        sitiosDisponibleAdapter =  ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,sitiosDisponibles)

        asientosCompartir.adapter =  sitiosDisponibleAdapter;

        cbAppleTVShare.isChecked =  false;

        cbAppleTVShare.setOnClickListener(View.OnClickListener {
            this.appleTv =  cbAppleTVShare.isChecked
        })

        cbBoardShared.setOnClickListener(View.OnClickListener {
            this.pizarra  = cbBoardShared.isChecked
        })


        selectAsientos()

        btnCreateOffer.setOnClickListener{createOffer()}

    }


    private fun createOffer(){
        var offer =  CreateOfferReservation(reservaId,appleTv,pizarra,sitios);

        Log.d("reservaId", offer.reservaId.toString());
        Log.d("apple", offer.apple.toString());
        Log.d("pizzarra", offer.pizarra.toString());
        Log.d("sitios", offer.sitios.toString());

        this.offerService.createOfferReservation(offer).enqueue(object :Callback<Any>{
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("error", "No se pudo crear correctamente la oferta")
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                goToReservation()
                Log.d("Exito", "Se ha realizado con exito la prueba")
            }


        })


    }

    private fun goToReservation(){
        val intent =  Intent(this,ReservationDetailActivity::class.java);
        intent.putExtra("idReservation",reservaId.toString())
        startActivity(intent)
        finish();
    }
    private fun selectAsientos(){
        asientosCompartir.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("seleccionado", "Nada")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sitios =  sitiosDisponibles[position]
            }

        }
    }
}
