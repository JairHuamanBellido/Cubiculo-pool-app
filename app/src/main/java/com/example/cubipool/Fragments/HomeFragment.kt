package com.example.cubipool.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Adapter.UserReservationAvailablesAdapter
import com.example.cubipool.HomeActivity
import com.example.cubipool.Interfaces.OnReservationAvailableListener
import com.example.cubipool.R
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.user.UserApiService
import com.example.cubipool.service.user.UserReservationsAvailables
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    protected lateinit var rootView: View

    lateinit var rvReservationsAvailables:RecyclerView
    var codigo = "";
    val userService =  ApiGateway().api.create<UserApiService>(UserApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_home, container, false)
        rvReservationsAvailables =  view.findViewById(R.id.rv_reservationsAvailables)

       rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initVariables()
        setupRecycleViewReservationsAvailables(inflater,container)
      initReservationAvailablesAdapter(HomeActivity())

        return view;
    }


    private fun initVariables(){
        codigo = this.activity!!.getSharedPreferences ("db_local",0).getString("code",null);
    }

    private fun setupRecycleViewReservationsAvailables( inflater: LayoutInflater,container: ViewGroup?){
        rvReservationsAvailables.layoutManager =  LinearLayoutManager(activity)
    }
    private fun initReservationAvailablesAdapter(contextActual: HomeActivity){
        userService.getReservationsAvailables("u201413797").enqueue(object:
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

                etNumeroReservas.text = "Actualmente tiene ${response.body()!!.size} reservas"

                rv_reservationsAvailables.adapter=UserReservationAvailablesAdapter(response.body()!!,contextActual)
            }

        })
    }


}
