package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Adapter.CubicleListAdapter
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.cubicle.CubicleResponse
import com.example.cubipool.service.cubicle.CubicleService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CubicleAvailablesActivity : AppCompatActivity() {
    lateinit var cubicleRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cubicle_availables)



        setupRecyclerView()





        val hour = intent.extras.getString("hours")
        val quantityHours =  intent.getStringExtra("quantityHours");
        val date =  intent.getStringExtra("date")






       val cubicleService =  ApiGateway().api.create<CubicleService>(CubicleService::class.java)

        cubicleService.findAllAvailableCubicles(date,hour,quantityHours).enqueue(object:Callback<ArrayList<CubicleResponse>>{
            override fun onFailure(call: Call<ArrayList<CubicleResponse>>, t: Throwable) {
                    println("Error")
            }

            override fun onResponse(
                call: Call<ArrayList<CubicleResponse>>,
                response: Response<ArrayList<CubicleResponse>>
            ) {


                cubicleRecyclerView.adapter = CubicleListAdapter(response.body()!!)
            }


        })


    }
    private fun setupRecyclerView() {
        val obj =  ArrayList<CubicleResponse>();
        obj.add(CubicleResponse(1,"123","3","2","3"))
        cubicleRecyclerView = findViewById<RecyclerView>(R.id.list_view)
        cubicleRecyclerView.layoutManager = LinearLayoutManager(this)
        cubicleRecyclerView.adapter = CubicleListAdapter(obj);
    }
}
