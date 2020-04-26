package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.cubicle.CubicleService
import kotlinx.android.synthetic.main.activity_search_cubicle.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchCubicleActivity : AppCompatActivity() {

    var hourSelected = ""
    var daySelected = "Hoy"
    var quantityHoursSelected = "1"
    var hours :MutableList<String> =  ArrayList()
    var hoursAvailable: MutableList<String> =  ArrayList()
    var dayAvailables:  MutableList<String>  =  ArrayList()

    var currentDateTime=LocalDateTime.now()
    var time = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    var actualHour =  time.toString().take(2).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_cubicle)

        val hoursAdapter =  ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,hours)
        val hoursAvailableAdapter =  ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,hoursAvailable)
        val dayAviablesAdapter =  ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,dayAvailables)
        hourSelected =  actualHour.toString();

        hourValidation()
        initDayAdapter(dayAviablesAdapter,hoursAvailableAdapter)
        initHoursAvailablesAdapter(hoursAvailableAdapter)
        initQuantityHoursAdapter(hoursAdapter)


        searchCubicleBtn.setOnClickListener { searchCubiclesAvailable(daySelected,hourSelected.toString(),quantityHoursSelected) }


    }

    /**
     * Valida  para mostrar la lista de horas disponibles a partir de la hora actual
     */
    private fun hourValidation(){

        /**
         * Validar horas para el dia de doy a partir de la hora actual
         */
        if(actualHour <= 22){

            /**
             * Muestra horarios a partir de las 7:00 am, si la hora actual se encuentra
             * en el rango de 00:00am - 06:59am
             */
            if(actualHour<=7){
                actualHour = 7;
            }
            addListHoursAvailable(hoursAvailable)
        }

        /**
         * Cuando selecciona la opción de mañana
         * Muestra todas las horas disponibles del dia sigueinte  7am - 10pm
         */
        else{
            setAllHoursAvailableForTomorrow(hoursAvailable);
            daySelected= "Mañana"
        }
    }

    /**
     * Generacion de horarios a partir de la hora actual del día actual
     */
    private fun addListHoursAvailable(hoursList:MutableList<String>){


        for( i in actualHour..22){
            if(i<10){
                hoursList.add("0$i:00")
            }else{
                hoursList.add("$i:00")
            }
        }
    }

    /**
     * Manda parámetros a la vista CubicleAvailablesActivity para mostrar
     * los cubículos disponibles en base a los parámetros
     */
    private fun searchCubiclesAvailable(date:String,hours: String,quantityHours:String){


        val intent =  Intent(getApplicationContext(), CubicleAvailablesActivity::class.java);

        intent.putExtra("date",date);
        intent.putExtra("hours", hours);
        intent.putExtra("quantityHours", quantityHours);
        intent.putExtra("second_user", etSecondCode.text.toString())

        startActivity(intent);

    }



    /**
     * Generacion de horarios a partir de las 7am hasta las 10pm para el día siguiente
     */
    private fun setAllHoursAvailableForTomorrow(hoursList: MutableList<String>){
        for(i in 7..22){
            if(i<10){
                hoursList.add("0$i:00")
            }else{
                hoursList.add("$i:00")
            }

        }
    }


    /**
     * Inicialización de lista de horarios para el consume del Adapter
     */
    private fun initHoursAvailablesAdapter(hoursAvailableAdapter:ArrayAdapter<String>){

        println("Entrando a setea horas")
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
    }

    /**
     * Inicializacion de lista de Dias (Hoy o Mañana) para el consumo del adapter
     */
    private fun initDayAdapter(dayAviablesAdapter:ArrayAdapter<String>, hoursAvailableAdapter:ArrayAdapter<String>){

        if(actualHour <= 22){
            dayAvailables.add("Hoy");
        }

        dayAvailables.add("Mañana");

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
                if(daySelected== "Mañana"){
                    println("Entrando a mañana")
                    hoursAvailable = ArrayList();
                    setAllHoursAvailableForTomorrow(hoursAvailable);
                    hoursAvailableAdapter.clear()
                    hoursAvailableAdapter.addAll(hoursAvailable);


                }
                else if (daySelected == "Hoy"){
                    hoursAvailable = ArrayList();
                    addListHoursAvailable(hoursAvailable)
                    hoursAvailableAdapter.clear()
                    hoursAvailableAdapter.addAll(hoursAvailable);

                }

            }

        }
    }


    /**
     * Inicializacion de lista de cantidad de horas para la reserva para el consumo del adapter
     */
    private fun initQuantityHoursAdapter(hoursAdapter:ArrayAdapter<String>){

        hoursAdapter.add("1")
        hoursAdapter.add("2")


        spinHours.adapter =  hoursAdapter
        spinHours.onItemSelectedListener =  object:AdapterView.OnItemSelectedListener {


            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("NS", "Nothing selected")
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
    }


}
