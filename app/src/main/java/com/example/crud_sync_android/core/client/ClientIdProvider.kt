package com.example.crud_sync_android.core.client


import android.content.Context
import java.util.UUID

class ClientIdProvider(
    private val context: Context
) {
    fun getClientId(): String {
        val prefs = context.getSharedPreferences(
            "client_pref",
            Context.MODE_PRIVATE
        )

        val existing = prefs.getString("client_id", null)

        if (existing != null) {
            return existing
        }

        val newId = "android-" + UUID.randomUUID().toString()

        prefs.edit()
            .putString("client_id", newId)
            .apply()

        return newId
    }
}