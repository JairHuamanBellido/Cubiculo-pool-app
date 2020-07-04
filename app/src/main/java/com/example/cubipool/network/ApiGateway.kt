package com.example.cubipool.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGateway {

     val api = Retrofit.Builder()
         .baseUrl("https://cubiculo-pool-upc-api.herokuapp.com/")
         //.baseUrl("http://192.168.1.9:3000/")
        .addConverterFactory(GsonConverterFactory.create()).build();
}