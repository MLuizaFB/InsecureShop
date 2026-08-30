package com.insecureshop.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.security.MessageDigest
import java.security.SecureRandom

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "insecureshop_prefs")

object Prefs {

    private val DATA_KEY = stringPreferencesKey("data")
    private val USERNAME_KEY = stringPreferencesKey("username")
    private val PASSWORD_KEY = stringPreferencesKey("password")
    private val PRODUCT_LIST_KEY = stringPreferencesKey("productList")

    suspend fun getData(context: Context): String {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }
            .map { preferences -> preferences[DATA_KEY] ?: "" }
            .first()
    }

    suspend fun getUsername(context: Context): String {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }
            .map { preferences -> preferences[USERNAME_KEY] ?: "" }
            .first()
    }

    suspend fun getPassword(context: Context): String {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }
            .map { preferences -> preferences[PASSWORD_KEY] ?: "" }
            .first()
    }

    suspend fun getProductList(context: Context): String {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }
            .map { preferences -> preferences[PRODUCT_LIST_KEY] ?: "" }
            .first()
    }

    suspend fun setData(context: Context, value: String) {
        context.dataStore.edit { preferences ->
            preferences[DATA_KEY] = value
        }
    }

    suspend fun setUsername(context: Context, value: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = value
        }
    }

    private fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    private fun hashPasswordWithSalt(password: String, salt: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(salt)
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))

        val saltHex = salt.joinToString("") { "%02x".format(it) }
        val hashHex = hashBytes.joinToString("") { "%02x".format(it) }

        return "$saltHex:$hashHex"
    }

    suspend fun setPassword(context: Context, value: String) {
        val salt = generateSalt()
        val saltedHash = hashPasswordWithSalt(value, salt)
        context.dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = saltedHash
        }
    }

    suspend fun setProductList(context: Context, value: String) {
        context.dataStore.edit { preferences ->
            preferences[PRODUCT_LIST_KEY] = value
        }
    }

    suspend fun clearAll(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}