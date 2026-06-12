package com.example.crud_sync_android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang")
data class BarangEntity(
    @PrimaryKey
    val id: Int,

    val kode: String,
    val nama: String,

    // Stok asli terakhir dari server
    val stokServer: Int,

    val harga: Double,

    // Total perubahan lokal yang belum berhasil sync
    // Contoh:
    // server = 10
    // pendingDelta = -2
    // stok tampil = 8
    val pendingDelta: Int = 0
)