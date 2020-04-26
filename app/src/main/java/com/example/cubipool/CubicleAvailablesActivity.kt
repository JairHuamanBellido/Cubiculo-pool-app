package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Adapter.CubicleListAdapter
import com.example.cubipool.Interfaces.OnCubicleClickListener
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.auth.AuthApiService
import com.example.cubipool.service.auth.AuthRequest
import com.example.cubipool.service.auth.AuthResponse
import com.example.cubipool.service.cubicle.CubicleResponse
import com.example.cubipool.service.cubicle.CubicleService
import com.example.cubipool.service.reservation.ReservationRequest
import com.example.cubipool.service.reservation.ReservationService
import kotlinx.android.synthetic.main.activity_cubicle_availables.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CubicleAvailablesActivity : AppCompatActivity(),OnCubicleClickListener {
    lateinit var cubicleRecyclerView: RecyclerView
    var cubiculo_id:Int = -1;
    val cubicleService =  ApiGateway().api.create<CubicleService>(CubicleService::class.java)
    val reservationService =  ApiGateway().api.create<ReservationService>(ReservationService::class.java)
    val authService =  ApiGateway().api.create<AuthApiService>(AuthApiService::class.java)
    var hour:String = "";
    var quantityHours:String="";
    var date:String = "";
    var codigo_dos = "";
    var codigo_uno ="";
    var hora_fin = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cubicle_availables)

        var contextActual = this;

        initVariables()
        setupRecyclerView()
        initCubicleAdapter(contextActual)


        btnSubmitReservation.setOnClickListener { reservationSubmit() }







    }

    /**
     * Inicialización de variables
     */
    private fun initVariables(){
        codigo_uno = getSharedPreferences("db_local",0).getString("code",null);


        hour = intent.extras.getString("hours")
        quantityHours =  intent.getStringExtra("quantityHours");
        date =  intent.getStringExtra("date")
        codigo_dos =  intent.getStringExtra("second_user");
        hora_fin = (hour.take(2).toInt() + quantityHours.toInt()).toString()+":00"
    }




    /**
     *     Llama al API para buscar los cubiculos disponibles
     */
    private fun initCubicleAdapter(contextActual:CubicleAvailablesActivity){


        /**
         * @param date ->  value:  "Hoy" o "Mañana"
         * @param hour -> value: "07:00"
         * @param quantityHours (int) ->  value: 1 o 2 . Descripcion: Cantidad de horas para la reserva
         */
        cubicleService.findAllAvailableCubicles(date,hour,quantityHours).enqueue(object:Callback<ArrayList<CubicleResponse>>{
            override fun onFailure(call: Call<ArrayList<CubicleResponse>>, t: Throwable) {
                println("Error")
            }

            override fun onResponse(
                call: Call<ArrayList<CubicleResponse>>,
                response: Response<ArrayList<CubicleResponse>>
            ) {

                cubicleRecyclerView.adapter = CubicleListAdapter(response.body()!!,contextActual)

            }


        })
    }



    /**
     * Inicia el recycle view en la vista
     */
    private fun setupRecyclerView() {

        cubicleRecyclerView = findViewById<RecyclerView>(R.id.list_view)
        cubicleRecyclerView.layoutManager = LinearLayoutManager(this)
    }



    /**
     * Evento escuchado del adapter cuando selecciona un cubículo
     * Se almacena el id para a futuro hacer una reserva con el id del cubículo
     */
    override fun onCubicleSelected(id: Int) {
         cubiculo_id =  id;
    }




    /**
     * Function accionada por el botón reservar
     */
    private fun reservationSubmit(){

        if(date == "Hoy"){
            date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        }
        else if(date== "Mañana"){
            date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE)
        }



        /**
         * @param date      -> "2020-04-17", formato : AAAA-MM-DD | String
         * @param hour      -> "13:00" | String
         * @param hora_fin  -> "15:00" | String
         * @param sede      -> "Monterrico" | String | Valor por defecto
         * @param codigo_uno-> "u201413797" | String
         * @param codigo_dos-> "u201413797" | String
         * @param cubiculo_id-> 2 | Int
         */
        reservationService.submitReservation(ReservationRequest(date,hour,hora_fin,"Monterrico",codigo_uno,codigo_dos,cubiculo_id)).enqueue(object:Callback<ReservationRequest>{
            override fun onFailure(call: Call<ReservationRequest>, t: Throwable) {
                Log.d("error", "Algo salio mal")
            }

            override fun onResponse(
                call: Call<ReservationRequest>,
                response: Response<ReservationRequest>
            ) {
                val intent =  Intent(applicationContext,ReservationSucessActivity::class.java)

                startActivity(intent);
                finish();
            }

        })

    }
}
