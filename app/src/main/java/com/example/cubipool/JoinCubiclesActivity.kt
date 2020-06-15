package com.example.cubipool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Adapter.JoinCubicleAdapter
import com.example.cubipool.Interfaces.OnCubicleClickListener
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.offers.CreateOfferResponse
import com.example.cubipool.service.offers.OfferService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinCubiclesActivity : AppCompatActivity(), OnCubicleClickListener {


    //lateinit var offerscubicles: List<CreateOfferResponse>
    lateinit var cubicleJoinRecyclerView: RecyclerView
    var offersService = ApiGateway().api.create<OfferService>(OfferService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_cubicles)

        var contextActual = this;

        setupRecyclerView()
        initJoinCubiclesAdapter(contextActual)

    }


    private fun initJoinCubiclesAdapter(contextActual: JoinCubiclesActivity){
        offersService.findAllOffers().enqueue(object :Callback<ArrayList<CreateOfferResponse>>{
            override fun onFailure(call: Call<ArrayList<CreateOfferResponse>>, t: Throwable) {
                print("error al buscar cubiculos para unirse")
            }

            override fun onResponse(
                call: Call<ArrayList<CreateOfferResponse>>,
                response: Response<ArrayList<CreateOfferResponse>>
            ) {

                cubicleJoinRecyclerView.adapter = JoinCubicleAdapter(response.body()!!,contextActual)

            }

        })
    }
    private fun setupRecyclerView(){
        cubicleJoinRecyclerView = findViewById<RecyclerView>(R.id.rvOffersJoin)
        cubicleJoinRecyclerView.layoutManager=LinearLayoutManager(this)
    }

    override fun onCubicleSelected(id: Int) {
      print("falta implementar")
    }


}
