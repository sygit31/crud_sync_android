package com.example.crud_sync_android.data.remote.dto

import com.google.gson.annotations.SerializedName

// ApiResponseDto dipakai untuk response sederhana seperti create, update, delete.
data class ApiResponseDto(

    // Menandakan request berhasil atau gagal.
    @SerializedName("success")
    val success: Boolean,

    // Pesan dari backend.
    @SerializedName("message")
    val message: String?
)