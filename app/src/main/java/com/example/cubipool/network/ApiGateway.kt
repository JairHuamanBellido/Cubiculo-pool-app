package com.example.cubipool.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGateway {

    public val api = Retrofit.Builder()
        .baseUrl("https://cubiculo-pool-upc-api.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create()).build();
}