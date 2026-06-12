package com.example.crud_sync_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.crud_sync_android.data.local.entity.BarangEntity
import com.example.crud_sync_android.data.local.entity.PendingStockMutationEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BarangDao {

    // UI membaca dari Room, bukan langsung dari API.
    @Query("SELECT * FROM barang ORDER BY id DESC")
    abstract fun observeBarang(): Flow<List<BarangEntity>>

    @Query("SELECT * FROM barang WHERE id = :id LIMIT 1")
    abstract suspend fun getBarangById(id: Int): BarangEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertBarang(barang: BarangEntity)

    // Ambil operasi stok yang belum dikirim ke server.
    @Query("SELECT * FROM pending_stock_mutation WHERE status = 'PENDING' ORDER BY localId ASC")
    abstract suspend fun getPendingMutations(): List<PendingStockMutationEntity>

    @Insert
    abstract suspend fun insertPendingMutation(entity: PendingStockMutationEntity)

    // pendingDelta ditambah saat user mengubah stok offline.
    @Query("""
        UPDATE barang 
        SET pendingDelta = pendingDelta + :delta 
        WHERE id = :barangId
    """)
    abstract suspend fun tambahPendingDelta(
        barangId: Int,
        delta: Int
    )

    // pendingDelta dikurangi setelah operasi sukses di server.
    @Query("""
        UPDATE barang 
        SET pendingDelta = pendingDelta - :delta 
        WHERE id = :barangId
    """)
    abstract suspend fun kurangiPendingDelta(
        barangId: Int,
        delta: Int
    )

    @Query("""
        UPDATE pending_stock_mutation 
        SET status = 'SYNCED' 
        WHERE operationId = :operationId
    """)
    abstract suspend fun markMutationSynced(operationId: String)

    @Query("""
        UPDATE pending_stock_mutation 
        SET status = 'FAILED', errorMessage = :message 
        WHERE operationId = :operationId
    """)
    abstract suspend fun markMutationFailed(
        operationId: String,
        message: String
    )

    // Saat sync sukses:
    // 1. Tandai operasi synced
    // 2. Kurangi pendingDelta
    //
    // Dibuat transaction agar dua proses ini tidak terpisah.
    @Transaction
    open suspend fun prosesMutationSukses(
        mutation: PendingStockMutationEntity
    ) {
        markMutationSynced(mutation.operationId)
        kurangiPendingDelta(
            barangId = mutation.barangId,
            delta = mutation.deltaStok
        )
    }

    // Saat user mengubah stok offline:
    // 1. pendingDelta barang berubah
    // 2. operasi masuk queue
    //
    // Dibuat transaction agar data lokal tidak setengah-setengah.
    @Transaction
    open suspend fun buatMutationOffline(
        barangId: Int,
        delta: Int,
        operationId: String
    ) {
        tambahPendingDelta(
            barangId = barangId,
            delta = delta
        )

        insertPendingMutation(
            PendingStockMutationEntity(
                operationId = operationId,
                barangId = barangId,
                deltaStok = delta
            )
        )
    }
}
