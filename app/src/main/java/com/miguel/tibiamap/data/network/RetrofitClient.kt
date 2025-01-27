package com.miguel.tibiamap.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient {    /**
 * @param url -> url del endpoint a consumir
 * @return Regresa una instancia de retrofit
 * */
fun getRetrofit(url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        //para traerme data de la api en texto plano
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
}