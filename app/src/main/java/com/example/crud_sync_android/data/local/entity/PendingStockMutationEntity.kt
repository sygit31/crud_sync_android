package com.example.crud_sync_android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_stock_mutation")
data class PendingStockMutationEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0,

    // ID unik operasi.
    // Wajib unik agar server tidak menghitung dua kali.
    val operationId: String,

    // Barang yang berubah stoknya
    val barangId: Int,

    // Perubahan stok.
    // Barang keluar = negatif.
    // Barang masuk = positif.
    val deltaStok: Int,

    // PENDING, SYNCED, FAILED
    val status: String = "PENDING",

    // Pesan error jika gagal sync
    val errorMessage: String? = null,

    // Waktu dibuat di Android
    val createdAt: Long = System.currentTimeMillis()
)