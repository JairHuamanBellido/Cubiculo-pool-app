package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.cubicle.CubicleResponse
import com.example.cubipool.service.cubicle.CubicleService
import kotlinx.android.synthetic.main.activity_search_cubicle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchCubicleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_cubicle)

        var hourSelected = ""
        var daySelected = "Hoy"
        var quantityHoursSelected = "1"


        var hours :MutableList<String> =  ArrayList()
        var hoursAvailable: MutableList<String> =  ArrayList()
        var dayAvailables:  MutableList<String>  =  ArrayList()

        // Variables para la generacion de horas para el dropdown
        var currentDateTime=LocalDateTime.now()
        var time = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        var actualHour =  time.toString().take(2).toInt()

        hourSelected =  actualHour.toString();

        // Añadir la cantidad de horas para reservas
        for(i in 1..2){
            hours.add("$i")
        }

        // Añadir todas las horas del dia  disponibles para la reserva
        addListHoursAvailable(hoursAvailable,actualHour)


        dayAvailables.add("Hoy");
        dayAvailables.add("Mañana");







        val hoursAdapter:ArrayAdapter<String> =  ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,hours)
        val hoursAvailableAdapter =  ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,hoursAvailable)
        val dayAviablesAdapter =  ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,dayAvailables)



        // Spin para mostrar dia de la fecha (Hoy o Mañana)
        spinDay.adapter =  dayAviablesAdapter;
        spinDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("Nothing to do")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                daySelected = dayAvailables[position]
            }

        }

        // Spin para mostrar las horas de reserva a partir del día actual
        spinHoursAvailable.adapter =  hoursAvailableAdapter;
        spinHoursAvailable.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                    println("Nothing selected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                hourSelected =  hoursAvailable[position];

            }

        }

        // Spin para mostrar la cantidad de horas de reserva
        spinHours.adapter =  hoursAdapter
        spinHours.onItemSelectedListener =  object:AdapterView.OnItemSelectedListener {


            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                quantityHoursSelected = hours[position];
            }


        }

        searchCubicleBtn.setOnClickListener { searchCubiclesAvailable(daySelected,hourSelected.toString(),quantityHoursSelected) }


    }

    private fun addListHoursAvailable(hoursList:MutableList<String>, hours:Int){
        for( i in hours..22){
            hoursList.add("$i:00")
        }
    }

    private fun searchCubiclesAvailable(date:String,hours: String,quantityHours:String){


        val cubicleService=  ApiGateway().api.create<CubicleService>(CubicleService::class.java)

        val intent =  Intent(getApplicationContext(), CubicleAvailablesActivity::class.java);

        intent.putExtra("date",date);
        intent.putExtra("hours", hours);
        intent.putExtra("quantityHours", quantityHours);

        startActivity(intent);





    }


}
