package com.example.crud_sync_android.core.di

import com.example.crud_sync_android.core.network.RetrofitClient
import com.example.crud_sync_android.data.remote.api.BarangApiService
import com.example.crud_sync_android.data.repository.BarangRepositoryImpl
import com.example.crud_sync_android.domain.repository.BarangRepository
import com.example.crud_sync_android.domain.usecase.CreateBarangUseCase
import com.example.crud_sync_android.domain.usecase.DeleteBarangUseCase
import com.example.crud_sync_android.domain.usecase.GetBarangListUseCase
import com.example.crud_sync_android.domain.usecase.UpdateBarangUseCase

// AppContainer adalah manual dependency injection sederhana.
// Nanti kalau project makin besar, bagian ini bisa diganti Hilt.
class AppContainer {

    // Membuat Retrofit sekali saja.
    private val retrofit = RetrofitClient.create()

    // Membuat API service untuk fitur barang.
    private val barangApiService: BarangApiService =
        retrofit.create(BarangApiService::class.java)

    // Repository interface diisi oleh implementasi repository.
    private val barangRepository: BarangRepository =
        BarangRepositoryImpl(
            apiService = barangApiService
        )

    // UseCase untuk mengambil daftar barang.
    val getBarangListUseCase = GetBarangListUseCase(
        repository = barangRepository
    )

    // UseCase untuk membuat barang baru.
    val createBarangUseCase = CreateBarangUseCase(
        repository = barangRepository
    )

    // UseCase untuk mengubah barang.
    val updateBarangUseCase = UpdateBarangUseCase(
        repository = barangRepository
    )

    // UseCase untuk menghapus barang.
    val deleteBarangUseCase = DeleteBarangUseCase(
        repository = barangRepository
    )
}