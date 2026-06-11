package com.example.crud_sync_android.domain.usecase

import com.example.crud_sync_android.core.result.AppResult
import com.example.crud_sync_android.domain.repository.BarangRepository

// UseCase ini bertugas menghapus barang.
class DeleteBarangUseCase(
    private val repository: BarangRepository
) {
    suspend operator fun invoke(id: Int): AppResult<String> {
        return repository.deleteBarang(id)
    }
}