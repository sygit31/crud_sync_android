package com.example.crud_sync_android.core.result

// AppResult adalah pembungkus hasil operasi.
// Tujuannya agar Repository tidak melempar error langsung ke ViewModel.
sealed interface AppResult<out T> {

    // Success dipakai ketika operasi berhasil dan membawa data.
    data class Success<T>(
        val data: T
    ) : AppResult<T>

    // Error dipakai ketika operasi gagal.
    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : AppResult<Nothing>
}
