package com.example.crud_sync_android.data.remote.api

import com.example.crud_sync_android.data.remote.dto.ApiResponseDto
import com.example.crud_sync_android.data.remote.dto.BarangListResponseDto
import com.example.crud_sync_android.data.remote.dto.BarangRequestDto
import com.example.crud_sync_android.data.remote.dto.SyncStockRequestDto
import com.example.crud_sync_android.data.remote.dto.SyncStockResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// BarangApiService berisi endpoint API untuk fitur barang.
interface BarangApiService {

    // Mengambil daftar barang.
    // Endpoint: GET /api/barang
    @GET("barang")
    suspend fun getBarangList(): BarangListResponseDto

    // Menambah barang baru.
    // Endpoint: POST /api/barang
    @POST("barang")
    suspend fun createBarang(
        @Body request: BarangRequestDto
    ): ApiResponseDto

    // Mengubah barang berdasarkan ID.
    // Endpoint: PUT /api/barang/{id}
    @PUT("barang/{id}")
    suspend fun updateBarang(
        @Path("id") id: Int,
        @Body request: BarangRequestDto
    ): ApiResponseDto

    // Menghapus barang berdasarkan ID.
    // Endpoint: DELETE /api/barang/{id}
    @DELETE("barang/{id}")
    suspend fun deleteBarang(
        @Path("id") id: Int
    ): ApiResponseDto

    //SYNCRON
    @POST("sync/stok")
    suspend fun syncStok(
        @Body request: SyncStockRequestDto
    ): SyncStockResponseDto
}