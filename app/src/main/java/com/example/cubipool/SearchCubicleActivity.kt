package com.example.cubipool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.cubipool.network.ApiGateway
import com.example.cubipool.service.cubicle.CubicleService
import com.example.cubipool.service.user.UserApiService
import com.example.cubipool.service.user.UserHoursAvailables
import com.example.cubipool.service.user.UserResponse
import kotlinx.android.synthetic.main.activity_search_cubicle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchCubicleActivity : AppCompatActivity() {

    var hourSelected = ""
    var daySelected = "Hoy"
    var quantityHoursSelected = "1"
    var hours: MutableList<String> = ArrayList()
    var hoursAvailable: MutableList<String> = ArrayList()
    var hoursToday: Int = 2
    var hoursTomorrow: Int = 2
    var dayAvailables: MutableList<String> = ArrayList()
    val userService = ApiGateway().api.create<UserApiService>(UserApiService::class.java)
    var codigo = "";
    var isListenerForFilterDay = false;
    lateinit var hoursAdapter: ArrayAdapter<String>;
    lateinit var hoursAvailableAdapter: ArrayAdapter<String>;
    lateinit var dayAviablesAdapter: ArrayAdapter<String>;

    var currentDateTime = LocalDateTime.now()
    var time = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    var actualHour = time.toString().take(2).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_cubicle)

        codigo = getSharedPreferences("db_local", 0).getString("code", null);
        hoursAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, hours)
        hoursAvailableAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, hoursAvailable)
        dayAviablesAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dayAvailables)
        hourSelected = actualHour.toString();

        findHoursAvailableToday(codigo)
        hourValidation()
        initHoursAvailablesAdapter(hoursAvailableAdapter)


        searchCubicleBtn.setOnClickListener {
            searchCubiclesAvailable(
                daySelected,
                hourSelected.toString(),
                quantityHoursSelected
            )
        }


    }

    /**
     * Valida  para mostrar la lista de horas disponibles a partir de la hora actual
     */
    private fun hourValidation() {

        /**
         * Validar horas para el dia de doy a partir de la hora actual
         */
        if (actualHour < 22) {

            /**
             * Muestra horarios a partir de las 7:00 am, si la hora actual se encuentra
             * en el rango de 00:00am - 06:59am
             */
            if (actualHour <= 7) {
                actualHour = 7;
            }
            addListHoursAvailable(hoursAvailable)
        }

        /**
         * Cuando selecciona la opción de mañana
         * Muestra todas las horas disponibles del dia sigueinte  7am - 10pm
         */
        else {
            setAllHoursAvailableForTomorrow(hoursAvailable);
            daySelected = "Mañana"
        }
    }

    /**
     * Generacion de horarios a partir de la hora actual del día actual
     */
    private fun addListHoursAvailable(hoursList: MutableList<String>) {


        for (i in actualHour..22) {
            if (i < 10) {
                hoursList.add("0$i:00")
            } else {
                hoursList.add("$i:00")
            }
        }
    }

    /**
     * Manda parámetros a la vista CubicleAvailablesActivity para mostrar
     * los cubículos disponibles en base a los parámetros
     */
    private fun searchCubiclesAvailable(date: String, hours: String, quantityHours: String) {



        if (codigo == etSecondCode.text.toString()) {
            Toast.makeText(
                applicationContext,
                "Ingrese un código diferente al suyo",
                Toast.LENGTH_SHORT
            ).show()
        } else {

            userService.findById(etSecondCode.text.toString()).enqueue(
                object : Callback<UserResponse> {
                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("Error", "Hubo un error la obtener el usuario")
                    }

                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {

                        Log.d("Status", response.code().toString())

                        when (response.code()) {
                            404 -> Toast.makeText(
                                applicationContext,
                                "El usuario no se encuentra registrado",
                                Toast.LENGTH_SHORT
                            ).show()
                            200 -> findAvailableHoursForSecondUser(
                                etSecondCode.text.toString(),
                                date,
                                quantityHours.toInt(),
                                hours
                            )
                        }

                    }

                }
            )

        }




    }

    private fun findAvailableHoursForSecondUser(
        secondeUserCode: String,
        date: String,
        quantityHours: Int,
        hours:String
    ) {




        userService.getHoursAvailablesByDay(etSecondCode.text.toString(), date).enqueue(
            object : Callback<UserHoursAvailables> {
                override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {
                    println("Hubo un error al reservar")

                }

                override fun onResponse(
                    call: Call<UserHoursAvailables>,
                    response: Response<UserHoursAvailables>
                ) {

                    if (response.body()!!.horasDisponibles >= quantityHours.toInt()) {
                        val intent = Intent(getApplicationContext(), CubicleAvailablesActivity::class.java);

                        intent.putExtra("date", date);
                        intent.putExtra("hours", hours);
                        intent.putExtra("quantityHours", quantityHours.toString());
                        intent.putExtra("second_user", etSecondCode.text.toString())
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "El código del segundo solo le queda ${response.body()!!.horasDisponibles} para reservar",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

            }
        )

    }

    /**
     * Generacion de horarios a partir de las 7am hasta las 10pm para el día siguiente
     */
    private fun setAllHoursAvailableForTomorrow(hoursList: MutableList<String>) {

        for (i in 7..22) {
            if (i < 10) {
                hoursList.add("0$i:00")
            } else {
                hoursList.add("$i:00")
            }

        }
    }


    /**
     * Inicialización de lista de horarios para el consume del Adapter
     */
    private fun initHoursAvailablesAdapter(hoursAvailableAdapter: ArrayAdapter<String>) {

        println("Entrando a setea horas")
        spinHoursAvailable.adapter = hoursAvailableAdapter;
        spinHoursAvailable.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("Nothing selected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                hourSelected = hoursAvailable[position];

            }

        }
    }

    /**
     * Inicializacion de lista de Dias (Hoy o Mañana) para el consumo del adapter
     */
    private fun initDayAdapter(
        dayAviablesAdapter: ArrayAdapter<String>,
        hoursAvailableAdapter: ArrayAdapter<String>
    ) {


        // Spin para mostrar dia de la fecha (Hoy o Mañana)
        spinDay.adapter = dayAviablesAdapter;
        spinDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                if (isListenerForFilterDay) {


                    if (daySelected == "Mañana") {
                        println("Entrando a mañana")
                        hoursAvailable = ArrayList();
                        setAllHoursAvailableForTomorrow(hoursAvailable);
                        hoursAvailableAdapter.clear()
                        hoursAvailableAdapter.addAll(hoursAvailable);

                        hoursAdapter.clear()
                        setHoursAdapater(hoursTomorrow)


                    } else if (daySelected == "Hoy") {
                        hoursAvailable = ArrayList();
                        addListHoursAvailable(hoursAvailable)
                        hoursAvailableAdapter.clear()
                        hoursAvailableAdapter.addAll(hoursAvailable);

                        hoursAdapter.clear()
                        setHoursAdapater(hoursToday)


                    }
                }

            }

        }
    }


    /**
     * Inicializacion de lista de cantidad de horas para la reserva para el consumo del adapter
     */
    private fun initQuantityHoursAdapter(hoursAdapter: ArrayAdapter<String>) {


        spinHours.adapter = hoursAdapter
        spinHours.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onNothingSelected(parent: AdapterView<*>?) {
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

    private fun findHoursAvailableToday(code: String) {


        userService.getHoursAvailablesByDay(code, "Hoy").enqueue(
            object : Callback<UserHoursAvailables> {
                override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<UserHoursAvailables>,
                    response: Response<UserHoursAvailables>
                ) {
                    if (response.body()?.horasDisponibles != 0) {

                        if (actualHour < 22) {
                            hoursToday = response.body()!!.horasDisponibles;
                            dayAvailables.add("Hoy");
                            daySelected = "Hoy"

                            setHoursAdapater(hoursToday)


                        }

                    }

                    findHoursAvailableTomorrow(code)


                }

            }
        )


    }

    private fun findHoursAvailableTomorrow(code: String) {

        userService.getHoursAvailablesByDay(code, "Mañana").enqueue(
            object : Callback<UserHoursAvailables> {
                override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<UserHoursAvailables>,
                    response: Response<UserHoursAvailables>
                ) {
                    if (response.body()?.horasDisponibles == 0) {
                    } else {

                        dayAvailables.add("Mañana")
                        daySelected = "Mañana"
                        hoursTomorrow = response.body()!!.horasDisponibles

                        if (hoursAdapter.isEmpty()) {
                            setHoursAdapater(hoursTomorrow)

                        }
                    }

                    initDayAdapter(dayAviablesAdapter, hoursAvailableAdapter)
                    initQuantityHoursAdapter(hoursAdapter)
                    isListenerForFilterDay = true;

                }

            }
        )


    }

    private fun setHoursAdapater(hours: Int) {

        if (hoursAdapter.isEmpty()) {


            if (hours == 2) {
                hoursAdapter.add("1")
                hoursAdapter.add("2")
            } else if (hours == 1) {
                hoursAdapter.add("1")

            }
        }
    }
}
