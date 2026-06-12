package com.example.crud_sync_android.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SyncStockRequestDto(
    @SerializedName("client_id")
    val clientId: String,

    @SerializedName("operations")
    val operations: List<StockOperationDto>
)

data class StockOperationDto(
    @SerializedName("operation_id")
    val operationId: String,

    @SerializedName("barang_id")
    val barangId: Int,

    @SerializedName("delta_stok")
    val deltaStok: Int,

    @SerializedName("client_created_at")
    val clientCreatedAt: String
)

data class SyncStockResponseDto(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: SyncStockDataDto?
)

data class SyncStockDataDto(
    @SerializedName("accepted")
    val accepted: List<SyncOperationResultDto> = emptyList(),

    @SerializedName("rejected")
    val rejected: List<SyncOperationResultDto> = emptyList()
)

data class SyncOperationResultDto(
    @SerializedName("operation_id")
    val operationId: String,

    @SerializedName("message")
    val message: String
)