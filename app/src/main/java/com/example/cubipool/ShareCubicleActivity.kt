package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.offers.CreateOfferReservation
import com.example.cubipool.service.offers.OfferService
import com.example.cubipool.service.offers.UpdateOfferModel
import com.example.cubipool.service.reservation.Offer
import kotlinx.android.synthetic.main.activity_share_cubicle.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShareCubicleActivity : AppCompatActivity() {
    lateinit var sitiosDisponibleAdapter:  ArrayAdapter<Int>;
     var reservaId:Int = 0;
    var offerId :Int = -1;
     var appleTv:Boolean = false;
     var pizarra:Boolean =  false;
    var sitios:Int =  3;

    var isEdit = false;
    val offerService =  ApiGateway().api.create<OfferService>(OfferService::class.java)
    var  sitiosDisponibles: MutableList<Int> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservaId = intent.getStringExtra("reservaId").toInt();




        setContentView(R.layout.activity_share_cubicle)
        sitiosDisponibles.add(1);
        sitiosDisponibles.add(2);
        sitiosDisponibles.add(3);
        sitiosDisponibles.add(4);

        sitiosDisponibleAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, sitiosDisponibles)

        asientosCompartir.adapter = sitiosDisponibleAdapter;

        this.initVariables()
        this.isEditActivity()

        btnCreateOffer.setOnClickListener { createOffer() }
        btnDeleteOffer.setOnClickListener { deleteOffer() }


        btn_backShareCubicle.setOnClickListener { back() }

    }


    private fun createOffer(){
        var offer =  CreateOfferReservation(this.reservaId,this.appleTv,this.pizarra,this.sitios);


        if(this.isEdit){


            this.offerService.updateOffer(this.offerId, UpdateOfferModel(this.appleTv,this.pizarra,this.sitios)).enqueue(object:Callback<Any>{
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("Hubo un error", "error");
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {

                    goToReservation();
                }
            })

        }
        else{
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

    private fun isEditActivity(){
        if(intent.getStringExtra("offerId") != null) {
            this.offerId =  intent.getStringExtra("offerId").toInt();
            this.isEdit =  true;
            btnDeleteOffer.visibility =View.VISIBLE;
            this.getOfferDetail()

        }


    }

    private fun initVariables(){
        btnDeleteOffer.visibility =  View.GONE


        cbAppleTVShare.setOnClickListener(View.OnClickListener {
            this.appleTv =  cbAppleTVShare.isChecked
        })

        cbBoardShared.setOnClickListener(View.OnClickListener {
            this.pizarra  = cbBoardShared.isChecked
        })


        selectAsientos()
    }

    private fun getOfferDetail(){
        offerService.findById(this.offerId).enqueue(object :Callback<CreateOfferReservation>{
            override fun onFailure(call: Call<CreateOfferReservation>, t: Throwable) {
                Log.d("error","Hubo un error al obtener la oferta detalle")
            }

            override fun onResponse(call: Call<CreateOfferReservation>, response: Response<CreateOfferReservation>) {
                cbAppleTVShare.isChecked  =  response.body()!!.apple
                cbBoardShared.isChecked =  response.body()!!.pizarra
                appleTv =  response.body()!!.apple;
                pizarra =  response.body()!!.pizarra;
                sitios =  response.body()!!.sitios
                asientosCompartir.setSelection(sitios -1);


            }
        })
    }

    private fun deleteOffer(){
       this.offerService.delete(this.offerId).enqueue(object:Callback<Any>{
           override fun onFailure(call: Call<Any>, t: Throwable) {
               Log.d("error", "error");
           }

           override fun onResponse(call: Call<Any>, response: Response<Any>) {
               goToReservation();
           }
       })
    }

    private fun back(){
        val intent =  Intent(this,ReservationDetailActivity::class.java )
        startActivity(intent);
        finish()
    }
}
