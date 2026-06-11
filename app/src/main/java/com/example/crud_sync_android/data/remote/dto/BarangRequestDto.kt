package com.example.crud_sync_android.data.remote.dto

import com.google.gson.annotations.SerializedName

// BarangRequestDto adalah bentuk data yang dikirim ke API saat create/update.
data class BarangRequestDto(

    // Kode barang yang dikirim ke backend.
    @SerializedName("kode")
    val kode: String,

    // Nama barang yang dikirim ke backend.
    @SerializedName("nama")
    val nama: String,

    // Stok barang yang dikirim ke backend.
    @SerializedName("stok")
    val stok: Int,

    // Harga barang yang dikirim ke backend.
    @SerializedName("harga")
    val harga: Double
)