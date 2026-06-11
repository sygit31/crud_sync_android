package com.example.crud_sync_android.domain.repository

import com.example.crud_sync_android.core.result.AppResult
import com.example.crud_sync_android.domain.model.Barang

// BarangRepository adalah kontrak data.
// ViewModel/UseCase cukup tahu kontrak ini, tidak perlu tahu Retrofit.
interface BarangRepository {

    // Mengambil semua barang dari server.
    suspend fun getBarangList(): AppResult<List<Barang>>

    // Membuat barang baru ke server.
    suspend fun createBarang(barang: Barang): AppResult<String>

    // Mengubah barang di server.
    suspend fun updateBarang(barang: Barang): AppResult<String>

    // Menghapus barang dari server.
    suspend fun deleteBarang(id: Int): AppResult<String>
}