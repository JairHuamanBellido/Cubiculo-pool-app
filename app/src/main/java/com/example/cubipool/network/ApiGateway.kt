package com.example.cubipool.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGateway {

    public val api = Retrofit.Builder()
        .baseUrl("http://192.168.1.16:3000/")
        .addConverterFactory(GsonConverterFactory.create()).build();
}