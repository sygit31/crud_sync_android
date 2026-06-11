package com.example.crud_sync_android.domain.usecase

import com.example.crud_sync_android.core.result.AppResult
import com.example.crud_sync_android.domain.model.Barang
import com.example.crud_sync_android.domain.repository.BarangRepository

// UseCase ini bertugas mengubah barang.
class UpdateBarangUseCase(
    private val repository: BarangRepository
) {
    suspend operator fun invoke(barang: Barang): AppResult<String> {
        return repository.updateBarang(barang)
    }
}