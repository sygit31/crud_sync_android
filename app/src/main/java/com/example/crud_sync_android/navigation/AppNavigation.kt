package com.example.crud_sync_android.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crud_sync_android.core.di.AppContainer
import com.example.crud_sync_android.presentation.barang.BarangScreen
import com.example.crud_sync_android.presentation.barang.BarangViewModel
import com.example.crud_sync_android.presentation.barang.BarangViewModelFactory

// AppNavigation mengatur perpindahan halaman aplikasi.
@Composable
fun AppNavigation(
    appContainer: AppContainer
) {
    // NavController adalah pengatur navigasi.
    val navController = rememberNavController()

    // NavHost adalah tempat mendaftarkan semua halaman.
    NavHost(
        navController = navController,
        startDestination = Routes.BARANG
    ) {
        // Halaman CRUD Barang.
        composable(Routes.BARANG) {

            // Membuat ViewModel dengan factory agar dependency bisa masuk.
            val barangViewModel: BarangViewModel = viewModel(
                factory = BarangViewModelFactory(
                    getBarangListUseCase = appContainer.getBarangListUseCase,
                    createBarangUseCase = appContainer.createBarangUseCase,
                    updateBarangUseCase = appContainer.updateBarangUseCase,
                    deleteBarangUseCase = appContainer.deleteBarangUseCase
                )
            )

            // Menampilkan screen barang.
            BarangScreen(
                viewModel = barangViewModel
            )
        }
    }
}