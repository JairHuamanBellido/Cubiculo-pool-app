package com.example.cubipool

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Adapter.UserReservationAvailablesAdapter
import com.example.cubipool.Interfaces.OnReservationAvailableListener
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.user.UserApiService
import com.example.cubipool.service.user.UserReservationsAvailables
import kotlinx.android.synthetic.main.activity_reservations_availables.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationsAvailablesActivity : AppCompatActivity(), OnReservationAvailableListener {
    lateinit var rvReservationsAvailables: RecyclerView
    val userService =  ApiGateway().api.create<UserApiService>(UserApiService::class.java)
    var codigo = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservations_availables)
        initVariables()
        val context =  this;
        rvReservationsAvailables =  findViewById(R.id.rv_reservationsAvailables)
        setupRecycleViewReservationsAvailables()
        initReservationAvailablesAdapter(context);


    }
    private fun setupRecycleViewReservationsAvailables(){
        rvReservationsAvailables.layoutManager =  LinearLayoutManager(this)
    }
    private fun initVariables(){
        codigo = getSharedPreferences ("db_local",0).getString("code",null);
    }

    private fun initReservationAvailablesAdapter(context: ReservationsAvailablesActivity){
        userService.getReservationsAvailables(codigo).enqueue(object:
            Callback<ArrayList<UserReservationsAvailables>> {
            override fun onFailure(
                call: Call<ArrayList<UserReservationsAvailables>>,
                t: Throwable
            ) {
                Log.d("error","Ha ocurrido un error")
            }

            override fun onResponse(
                call: Call<ArrayList<UserReservationsAvailables>>,
                response: Response<ArrayList<UserReservationsAvailables>>
            ) {
                Log.d("Carga exitosa", "Exitos")
                Log.d("Numero de reservas", response.body()!!.size.toString() )


                rv_reservationsAvailables.adapter=
                        UserReservationAvailablesAdapter(response.body()!!,context)
            }

        })
    }

    override fun onItemSelected(userReservationsAvailables: UserReservationsAvailables) {
        Log.d("Id reserva",userReservationsAvailables.id.toString())

        val intent =  Intent(this,ReservationDetailActivity::class.java)
        intent.putExtra("idReservation",userReservationsAvailables.id.toString())

        startActivity(intent)
    }
}
