package com.example.crud_sync_android.data.remote.dto

import com.google.gson.annotations.SerializedName

// BarangListResponseDto adalah response dari endpoint daftar barang.
data class BarangListResponseDto(

    // Menandakan request berhasil atau gagal.
    @SerializedName("success")
    val success: Boolean,

    // Pesan dari backend.
    @SerializedName("message")
    val message: String?,

    // Data daftar barang dari API.
    @SerializedName("data")
    val data: List<BarangDto>?
)