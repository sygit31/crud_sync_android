package com.example.crud_sync_android.data.mapper

import com.example.crud_sync_android.data.local.entity.BarangEntity
import com.example.crud_sync_android.data.remote.dto.BarangDto
import com.example.crud_sync_android.domain.model.Barang

// Mengubah data dari Room ke Domain.
// UI akan membaca data dari Room, lalu diubah ke Barang domain.
fun BarangEntity.toDomain(): Barang {
    return Barang(
        id = id,
        kode = kode,
        nama = nama,

        // Stok yang tampil = stok dari server + perubahan lokal yang belum sync
        stok = stokServer + pendingDelta,

        harga = harga
    )
}

// Mengubah data dari API menjadi Entity Room.
// Ini dipakai saat refresh data dari server lalu disimpan ke Room.
fun BarangDto.toEntity(
    pendingDelta: Int = 0
): BarangEntity {
    return BarangEntity(
        id = id,
        kode = kode,
        nama = nama,
        stokServer = stok,
        harga = harga,

        // pendingDelta lama harus dijaga agar perubahan offline tidak hilang
        pendingDelta = pendingDelta
    )
}