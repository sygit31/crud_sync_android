package com.example.crud_sync_android.domain.model

// Barang adalah model utama aplikasi.
// Model ini bersih dari Retrofit, Room, Gson, atau detail API.
data class Barang(
    val id: Int,
    val kode: String,
    val nama: String,
    val stok: Int,
    val harga: Double
)
