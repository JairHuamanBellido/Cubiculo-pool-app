package com.example.cubipool.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.cubipool.Adapter.UserReservationAvailablesAdapter
import com.example.cubipool.HomeActivity
import com.example.cubipool.Interfaces.OnReservationAvailableListener
import com.example.cubipool.R
import com.example.cubipool.ReservationsAvailablesActivity
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
    lateinit var btnNavigation:Button;
    var codigo = "";
    val userService =  ApiGateway().api.create<UserApiService>(UserApiService::class.java)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_home, container, false)

       rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initVariables()
        codigo=  this.activity!!.getSharedPreferences("db_local",0).getString("code",null);

        return view;
    }

    override fun onResume() {
        super.onResume()
        getQuantityReservationsAvailables()

    }
    private fun getQuantityReservationsAvailables(){
        userService.getReservationsAvailables(codigo).enqueue(object :Callback<ArrayList<UserReservationsAvailables>>{
            override fun onFailure(
                call: Call<ArrayList<UserReservationsAvailables>>,
                t: Throwable
            ) {
                Log.d("error","No se pudo cargar las reservas")
            }

            override fun onResponse(
                call: Call<ArrayList<UserReservationsAvailables>>,
                response: Response<ArrayList<UserReservationsAvailables>>
            ) {
                Log.d("qwe", response.body()!!.size.toString())

                imageView2.visibility =  View.VISIBLE
                textView18.visibility =  View.VISIBLE
                etNumeroReservas.visibility =  View.VISIBLE
                loadingHomeFragment.visibility =  View.GONE
                tv_loadingText_HomeFragment.visibility =  View.GONE
                if(response.body()!!.size> 0){
                    btn_viewReservations.visibility = View.VISIBLE
                    etNumeroReservas.text = "Actualmente tienes reservas"

                }

            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_viewReservations.visibility  =View.GONE
        btn_viewReservations.setOnClickListener { navigateToReservationsAvailableActivity() }
    }

    private fun initVariables(){
        codigo = this.activity!!.getSharedPreferences ("db_local",0).getString("code",null);
    }


    private fun navigateToReservationsAvailableActivity(){
        Log.d("qwe", "Navengando...")
        val intent =  Intent(context,ReservationsAvailablesActivity::class.java)
        startActivity(intent);

    }


}
