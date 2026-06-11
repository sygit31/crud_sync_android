package com.example.crud_sync_android.presentation.barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_sync_android.core.result.AppResult
import com.example.crud_sync_android.domain.model.Barang
import com.example.crud_sync_android.domain.usecase.CreateBarangUseCase
import com.example.crud_sync_android.domain.usecase.DeleteBarangUseCase
import com.example.crud_sync_android.domain.usecase.GetBarangListUseCase
import com.example.crud_sync_android.domain.usecase.UpdateBarangUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// BarangViewModel menyimpan state dan logic halaman barang.
class BarangViewModel(
    private val getBarangListUseCase: GetBarangListUseCase,
    private val createBarangUseCase: CreateBarangUseCase,
    private val updateBarangUseCase: UpdateBarangUseCase,
    private val deleteBarangUseCase: DeleteBarangUseCase
) : ViewModel() {

    // _uiState hanya boleh diubah dari dalam ViewModel.
    private val _uiState = MutableStateFlow(BarangUiState())

    // uiState boleh dibaca oleh Screen.
    val uiState: StateFlow<BarangUiState> = _uiState.asStateFlow()

    init {
        // Mengambil data pertama kali saat ViewModel dibuat.
        loadBarang()
    }

    // Mengambil daftar barang dari server.
    fun loadBarang() {
        viewModelScope.launch {
            loadBarangInternal()
        }
    }

    // Fungsi internal agar bisa dipakai ulang setelah create/update/delete.
    private suspend fun loadBarangInternal(
        successMessage: String? = null
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )
        }

        when (val result = getBarangListUseCase()) {
            is AppResult.Success -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        barangList = result.data,
                        successMessage = successMessage
                    )
                }
            }

            is AppResult.Error -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
    }

    // Mengubah input kode barang.
    fun onKodeChange(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                form = currentState.form.copy(kode = value),
                errorMessage = null,
                successMessage = null
            )
        }
    }

    // Mengubah input nama barang.
    fun onNamaChange(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                form = currentState.form.copy(nama = value),
                errorMessage = null,
                successMessage = null
            )
        }
    }

    // Mengubah input stok barang.
    fun onStokChange(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                form = currentState.form.copy(stok = value),
                errorMessage = null,
                successMessage = null
            )
        }
    }

    // Mengubah input harga barang.
    fun onHargaChange(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                form = currentState.form.copy(harga = value),
                errorMessage = null,
                successMessage = null
            )
        }
    }

    // Menyimpan data, bisa tambah baru atau update.
    fun onSimpanClick() {
        val barang = validateForm() ?: return
        val isEditMode = _uiState.value.isEditMode

        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            val result = if (isEditMode) {
                updateBarangUseCase(barang)
            } else {
                createBarangUseCase(barang)
            }

            when (result) {
                is AppResult.Success -> {
                    resetFormOnly()
                    loadBarangInternal(
                        successMessage = result.data
                    )
                }

                is AppResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    // Memilih data untuk diedit.
    fun onEditClick(barang: Barang) {
        _uiState.update { currentState ->
            currentState.copy(
                form = BarangFormState(
                    id = barang.id,
                    kode = barang.kode,
                    nama = barang.nama,
                    stok = barang.stok.toString(),
                    harga = barang.harga.toString()
                ),
                isEditMode = true,
                errorMessage = null,
                successMessage = null
            )
        }
    }

    // Menghapus data barang.
    fun onDeleteClick(id: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            when (val result = deleteBarangUseCase(id)) {
                is AppResult.Success -> {
                    loadBarangInternal(
                        successMessage = result.data
                    )
                }

                is AppResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    // Mereset form input.
    fun onResetFormClick() {
        resetFormOnly()
    }

    // Validasi form sebelum dikirim ke API.
    private fun validateForm(): Barang? {
        val form = _uiState.value.form

        if (form.kode.isBlank()) {
            setError("Kode barang wajib diisi")
            return null
        }

        if (form.nama.isBlank()) {
            setError("Nama barang wajib diisi")
            return null
        }

        val stokInt = form.stok.toIntOrNull()
        if (stokInt == null) {
            setError("Stok harus angka")
            return null
        }

        val hargaDouble = form.harga.toDoubleOrNull()
        if (hargaDouble == null) {
            setError("Harga harus angka")
            return null
        }

        return Barang(
            id = form.id ?: 0,
            kode = form.kode.trim(),
            nama = form.nama.trim(),
            stok = stokInt,
            harga = hargaDouble
        )
    }

    // Mengisi pesan error ke UiState.
    private fun setError(message: String) {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = message,
                successMessage = null
            )
        }
    }

    // Reset form tanpa menghapus daftar barang.
    private fun resetFormOnly() {
        _uiState.update { currentState ->
            currentState.copy(
                form = BarangFormState(),
                isEditMode = false,
                errorMessage = null,
                successMessage = null
            )
        }
    }
}