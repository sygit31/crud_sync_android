package com.example.crud_sync_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

//TAMBAHAN
import com.example.crud_sync_android.navigation.AppNavigation

// MainActivity hanya menjadi pintu masuk aplikasi.
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengambil AppContainer dari MyApp.
        val appContainer = (application as MyApp).appContainer

        // Menampilkan navigation utama aplikasi.
        setContent {
            AppNavigation(
                appContainer = appContainer
            )
        }
    }
}
