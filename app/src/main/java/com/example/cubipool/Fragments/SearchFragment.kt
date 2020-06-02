package com.example.cubipool.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.cubipool.HomeActivity

import com.example.cubipool.R
import com.example.cubipool.SearchCubicleActivity
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.user.UserApiService
import com.example.cubipool.service.user.UserHoursAvailables
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {


    lateinit var buttonSearch :Button;
    lateinit var tvStatusCubicle:TextView;
    val userService =  ApiGateway().api.create<UserApiService>(UserApiService::class.java)
    var codigo = "";
    var isAvailableForReservation = true;
    var currentDateTime = LocalDateTime.now()

    var time = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    var actualHour = time.toString().take(2).toInt()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            
         var view = inflater.inflate(R.layout.fragment_search, container, false)
        initVariables(view)
        findHoursAvailableToday(HomeActivity())

        return view;


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchCubicleBtn.setOnClickListener { goToSearchCubicle() }
    }

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }

    }
    private fun initVariables(view: View){
        codigo = this.activity!!.getSharedPreferences ("db_local",0).getString("code",null);
        buttonSearch =  view.findViewById(R.id.searchCubicleBtn)
        tvStatusCubicle =  view.findViewById(R.id.tv_statusCubicle)

        buttonSearch.visibility = View.GONE;

    }
    private fun goToSearchCubicle() {

        val intent = Intent(context, SearchCubicleActivity::class.java)

        startActivity(intent);
    }


    private fun decideToHideSearchButton(){
        tvStatusCubicle.text = "Tienes el máximo de horas de reserva para el día de hoy y mañana"
        buttonSearch.visibility = (View.GONE)
    }
    private fun findHoursAvailableToday(context: HomeActivity){


        userService.getHoursAvailablesByDay(codigo,"Hoy").enqueue(
            object:Callback<UserHoursAvailables>{
                override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {
                    Log.d("Error", "Algo salio mal en solicitar las horas de disponibilidad")
                    Log.d("ga", t.toString())
                }

                override fun onResponse(
                    call: Call<UserHoursAvailables>,
                    response: Response<UserHoursAvailables>
                ) {
                    if(response.body()?.horasDisponibles ==0 || actualHour >= 22){
                        isAvailableForReservation =  false;
                        findHoursAvailableTomorrow(context)

                    }
                    else{
                        buttonSearch.visibility = View.VISIBLE;

                    }
                }

            }
        )



    }
    private fun findHoursAvailableTomorrow(context: HomeActivity){

        userService.getHoursAvailablesByDay(codigo,"Mañana").enqueue(
            object :Callback<UserHoursAvailables>{
                override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<UserHoursAvailables>,
                    response: Response<UserHoursAvailables>
                ) {
                    if(response.body()?.horasDisponibles==0){
                        decideToHideSearchButton()
                    }else{
                        buttonSearch.visibility = View.VISIBLE;
                    }
                }

            }
        )


    }
}

