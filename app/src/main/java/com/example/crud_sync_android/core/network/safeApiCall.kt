package com.example.crud_sync_android.core.network

import com.example.crud_sync_android.core.result.AppResult
import retrofit2.HttpException
import java.io.IOException

// safeApiCall membungkus panggilan API agar error tidak membuat aplikasi langsung crash.
suspend fun <T> safeApiCall(
    block: suspend () -> T
): AppResult<T> {
    return try {
        // Menjalankan request API.
        val result = block()

        // Mengembalikan hasil jika request berhasil.
        AppResult.Success(result)
    } catch (e: IOException) {
        // Error ini biasanya muncul jika internet mati, server tidak aktif, atau IP salah.
        AppResult.Error(
            message = "Tidak bisa konek ke server. Cek internet, IP server, atau XAMPP.",
            throwable = e
        )
    } catch (e: HttpException) {
        // Error ini muncul jika server memberi HTTP error, misalnya 404 atau 500.
        AppResult.Error(
            message = "Terjadi error HTTP: ${e.code()}",
            throwable = e
        )
    } catch (e: Exception) {
        // Error umum lainnya.
        AppResult.Error(
            message = e.message ?: "Terjadi kesalahan tidak dikenal",
            throwable = e
        )
    }
}