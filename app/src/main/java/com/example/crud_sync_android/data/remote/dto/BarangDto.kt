package com.example.crud_sync_android.data.remote.dto

import com.google.gson.annotations.SerializedName

// BarangDto adalah bentuk data yang diterima dari API.
// DTO boleh mengikuti nama field dari backend.
data class BarangDto(

    // ID barang dari database MySQL.
    @SerializedName("id")
    val id: Int,

    // Kode barang, contoh BRG001.
    @SerializedName("kode")
    val kode: String,

    // Nama barang.
    @SerializedName("nama")
    val nama: String,

    // Jumlah stok barang.
    @SerializedName("stok")
    val stok: Int,

    // Harga barang.
    @SerializedName("harga")
    val harga: Double
)