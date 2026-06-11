package com.example.crud_sync_android

import android.app.Application
import com.example.crud_sync_android.core.di.AppContainer

// Class Application dipakai untuk membuat object global yang hidup selama aplikasi berjalan.
class MyApp : Application() {

    // AppContainer menyimpan dependency aplikasi, misalnya Retrofit, Repository, dan UseCase.
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        // Membuat dependency utama saat aplikasi pertama kali dijalankan.
        appContainer = AppContainer()
    }
}
