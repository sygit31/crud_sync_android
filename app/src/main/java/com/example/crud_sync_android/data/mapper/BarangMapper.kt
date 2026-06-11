package com.example.crud_sync_android.data.mapper

import com.example.crud_sync_android.data.remote.dto.BarangDto
import com.example.crud_sync_android.data.remote.dto.BarangRequestDto
import com.example.crud_sync_android.domain.model.Barang

// Mengubah BarangDto dari API menjadi Barang domain model.
fun BarangDto.toDomain(): Barang {
    return Barang(
        id = id,
        kode = kode,
        nama = nama,
        stok = stok,
        harga = harga
    )
}

// Mengubah Barang domain model menjadi BarangRequestDto untuk dikirim ke API.
fun Barang.toRequestDto(): BarangRequestDto {
    return BarangRequestDto(
        kode = kode,
        nama = nama,
        stok = stok,
        harga = harga
    )
}