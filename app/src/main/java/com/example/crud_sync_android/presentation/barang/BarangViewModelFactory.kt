package com.example.crud_sync_android.presentation.barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crud_sync_android.domain.usecase.CreateBarangUseCase
import com.example.crud_sync_android.domain.usecase.DeleteBarangUseCase
import com.example.crud_sync_android.domain.usecase.GetBarangListUseCase
import com.example.crud_sync_android.domain.usecase.UpdateBarangUseCase

// Factory dipakai karena BarangViewModel membutuhkan parameter UseCase.
class BarangViewModelFactory(
    private val getBarangListUseCase: GetBarangListUseCase,
    private val createBarangUseCase: CreateBarangUseCase,
    private val updateBarangUseCase: UpdateBarangUseCase,
    private val deleteBarangUseCase: DeleteBarangUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        // Mengecek apakah ViewModel yang diminta adalah BarangViewModel.
        if (modelClass.isAssignableFrom(BarangViewModel::class.java)) {
            return BarangViewModel(
                getBarangListUseCase = getBarangListUseCase,
                createBarangUseCase = createBarangUseCase,
                updateBarangUseCase = updateBarangUseCase,
                deleteBarangUseCase = deleteBarangUseCase
            ) as T
        }

        // Error jika factory dipakai untuk ViewModel yang salah.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}