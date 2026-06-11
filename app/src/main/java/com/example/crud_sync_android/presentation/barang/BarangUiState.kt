package com.example.crud_sync_android.presentation.barang

import com.example.crud_sync_android.domain.model.Barang

// BarangFormState menyimpan isi form input barang.
data class BarangFormState(

    // ID null berarti mode tambah data baru.
    val id: Int? = null,

    // Input kode barang.
    val kode: String = "",

    // Input nama barang.
    val nama: String = "",

    // Input stok disimpan String agar mudah dipakai di TextField.
    val stok: String = "",

    // Input harga disimpan String agar mudah dipakai di TextField.
    val harga: String = ""
)

// BarangUiState menyimpan seluruh state halaman barang.
data class BarangUiState(

    // Loading true saat sedang proses API.
    val isLoading: Boolean = false,

    // Daftar barang yang ditampilkan.
    val barangList: List<Barang> = emptyList(),

    // State form input.
    val form: BarangFormState = BarangFormState(),

    // True jika user sedang edit data.
    val isEditMode: Boolean = false,

    // Pesan error untuk UI.
    val errorMessage: String? = null,

    // Pesan sukses untuk UI.
    val successMessage: String? = null
)