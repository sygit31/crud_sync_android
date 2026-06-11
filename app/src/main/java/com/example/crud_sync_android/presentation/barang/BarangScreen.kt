package com.example.crud_sync_android.presentation.barang

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.crud_sync_android.domain.model.Barang

// BarangScreen menghubungkan ViewModel dengan UI.
@Composable
fun BarangScreen(
    viewModel: BarangViewModel
) {
    // Membaca UiState dari ViewModel secara aman mengikuti lifecycle.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Mengirim state dan event ke content.
    BarangContent(
        uiState = uiState,
        onKodeChange = viewModel::onKodeChange,
        onNamaChange = viewModel::onNamaChange,
        onStokChange = viewModel::onStokChange,
        onHargaChange = viewModel::onHargaChange,
        onSimpanClick = viewModel::onSimpanClick,
        onResetFormClick = viewModel::onResetFormClick,
        onEditClick = viewModel::onEditClick,
        onDeleteClick = viewModel::onDeleteClick,
        onRefreshClick = viewModel::loadBarang
    )
}

// BarangContent berisi tampilan utama halaman barang.
@Composable
private fun BarangContent(
    uiState: BarangUiState,
    onKodeChange: (String) -> Unit,
    onNamaChange: (String) -> Unit,
    onStokChange: (String) -> Unit,
    onHargaChange: (String) -> Unit,
    onSimpanClick: () -> Unit,
    onResetFormClick: () -> Unit,
    onEditClick: (Barang) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Judul halaman.
        Text(
            text = "CRUD Barang",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Form input barang.
        BarangForm(
            uiState = uiState,
            onKodeChange = onKodeChange,
            onNamaChange = onNamaChange,
            onStokChange = onStokChange,
            onHargaChange = onHargaChange,
            onSimpanClick = onSimpanClick,
            onResetFormClick = onResetFormClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Menampilkan pesan sukses jika ada.
        if (uiState.successMessage != null) {
            Text(
                text = uiState.successMessage
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Menampilkan pesan error jika ada.
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Tombol refresh data dari API.
        Button(
            onClick = onRefreshClick,
            enabled = !uiState.isLoading
        ) {
            Text("Refresh Data")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Status loading.
        if (uiState.isLoading) {
            Text("Loading...")
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Daftar barang.
        BarangList(
            barangList = uiState.barangList,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick
        )
    }
}

// BarangForm berisi input kode, nama, stok, dan harga.
@Composable
private fun BarangForm(
    uiState: BarangUiState,
    onKodeChange: (String) -> Unit,
    onNamaChange: (String) -> Unit,
    onStokChange: (String) -> Unit,
    onHargaChange: (String) -> Unit,
    onSimpanClick: () -> Unit,
    onResetFormClick: () -> Unit
) {
    val form = uiState.form

    Column {
        // Label mode form.
        Text(
            text = if (uiState.isEditMode) "Edit Barang" else "Tambah Barang",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Input kode barang.
        OutlinedTextField(
            value = form.kode,
            onValueChange = onKodeChange,
            label = {
                Text("Kode Barang")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input nama barang.
        OutlinedTextField(
            value = form.nama,
            onValueChange = onNamaChange,
            label = {
                Text("Nama Barang")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input stok barang.
        OutlinedTextField(
            value = form.stok,
            onValueChange = onStokChange,
            label = {
                Text("Stok")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input harga barang.
        OutlinedTextField(
            value = form.harga,
            onValueChange = onHargaChange,
            label = {
                Text("Harga")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            // Tombol simpan, bisa untuk tambah atau update.
            Button(
                onClick = onSimpanClick,
                enabled = !uiState.isLoading
            ) {
                Text(
                    text = if (uiState.isEditMode) "Update" else "Simpan"
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Tombol reset form.
            Button(
                onClick = onResetFormClick,
                enabled = !uiState.isLoading
            ) {
                Text("Reset")
            }
        }
    }
}

// BarangList menampilkan daftar barang dari API.
@Composable
private fun BarangList(
    barangList: List<Barang>,
    onEditClick: (Barang) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Column {
        // Judul daftar barang.
        Text(
            text = "Daftar Barang",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (barangList.isEmpty()) {
            // Tampilan jika data kosong.
            Text("Belum ada data barang")
        } else {
            // LazyColumn dipakai untuk list yang bisa banyak.
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = barangList,
                    key = { barang -> barang.id }
                ) { barang ->
                    BarangItem(
                        barang = barang,
                        onEditClick = onEditClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }
    }
}

// BarangItem menampilkan satu item barang.
@Composable
private fun BarangItem(
    barang: Barang,
    onEditClick: (Barang) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Informasi barang.
            Text("ID: ${barang.id}")
            Text("Kode: ${barang.kode}")
            Text("Nama: ${barang.nama}")
            Text("Stok: ${barang.stok}")
            Text("Harga: ${barang.harga}")

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                // Tombol edit akan mengisi form dengan data yang dipilih.
                Button(
                    onClick = {
                        onEditClick(barang)
                    }
                ) {
                    Text("Edit")
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Tombol hapus akan menghapus data berdasarkan ID.
                Button(
                    onClick = {
                        onDeleteClick(barang.id)
                    }
                ) {
                    Text("Hapus")
                }
            }
        }
    }
}