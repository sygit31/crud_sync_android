package com.example.crud_sync_android.data.repository

import com.example.crud_sync_android.core.network.safeApiCall
import com.example.crud_sync_android.core.result.AppResult
import com.example.crud_sync_android.data.mapper.toDomain
import com.example.crud_sync_android.data.mapper.toRequestDto
import com.example.crud_sync_android.data.remote.api.BarangApiService
import com.example.crud_sync_android.domain.model.Barang
import com.example.crud_sync_android.domain.repository.BarangRepository

// BarangRepositoryImpl adalah implementasi repository yang memakai Retrofit API.
class BarangRepositoryImpl(
    private val apiService: BarangApiService
) : BarangRepository {

    // Mengambil daftar barang dari API lalu mengubah DTO menjadi domain model.
    override suspend fun getBarangList(): AppResult<List<Barang>> {
        return safeApiCall {
            val response = apiService.getBarangList()

            if (response.success) {
                response.data.orEmpty().map { barangDto ->
                    barangDto.toDomain()
                }
            } else {
                throw IllegalStateException(
                    response.message ?: "Gagal mengambil data barang"
                )
            }
        }
    }

    // Menambah barang baru ke API.
    override suspend fun createBarang(barang: Barang): AppResult<String> {
        return safeApiCall {
            val response = apiService.createBarang(
                request = barang.toRequestDto()
            )

            if (response.success) {
                response.message ?: "Barang berhasil disimpan"
            } else {
                throw IllegalStateException(
                    response.message ?: "Gagal menyimpan barang"
                )
            }
        }
    }

    // Mengubah barang ke API berdasarkan ID.
    override suspend fun updateBarang(barang: Barang): AppResult<String> {
        return safeApiCall {
            val response = apiService.updateBarang(
                id = barang.id,
                request = barang.toRequestDto()
            )

            if (response.success) {
                response.message ?: "Barang berhasil diubah"
            } else {
                throw IllegalStateException(
                    response.message ?: "Gagal mengubah barang"
                )
            }
        }
    }

    // Menghapus barang dari API berdasarkan ID.
    override suspend fun deleteBarang(id: Int): AppResult<String> {
        return safeApiCall {
            val response = apiService.deleteBarang(
                id = id
            )

            if (response.success) {
                response.message ?: "Barang berhasil dihapus"
            } else {
                throw IllegalStateException(
                    response.message ?: "Gagal menghapus barang"
                )
            }
        }
    }
}