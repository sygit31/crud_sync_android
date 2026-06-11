package com.example.crud_sync_android.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// RetrofitClient bertugas membuat object Retrofit.
object RetrofitClient {

    // Untuk emulator Android ke XAMPP laptop, gunakan 10.0.2.2.
    // Ganti nama_project sesuai folder CI3 Anda.
    private const val BASE_URL = "http://192.168.17.50/api_ci4/public/android/"

    // Fungsi ini membuat Retrofit yang akan dipakai API Service.
    fun create(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}