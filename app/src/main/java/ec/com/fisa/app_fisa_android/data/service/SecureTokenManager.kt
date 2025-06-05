package ec.com.fisa.app_fisa_android.data.service

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.qualifiers.ApplicationContext
import ec.com.fisa.app_fisa_android.core.exception.TokenStorageException
import ec.com.fisa.app_fisa_android.data.datasource.dto.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SecureTokenManager @Inject constructor(@ApplicationContext private val context: Context){

    private val gson = Gson()

    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val encryptedSharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            "secure_token_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val TOKEN_DATA_KEY = "token_data_object"
    }

    // Guardar objeto token completo
    fun saveTokenData(loginResponse: LoginResponse) {
        try {
            val tokenJson = gson.toJson(loginResponse)
            encryptedSharedPreferences.edit()
                .putString(TOKEN_DATA_KEY, tokenJson)
                .apply()
        } catch (e: Exception) {
            throw TokenStorageException("Error al guardar token", e)
        }
    }

    // Obtener objeto token completo
    fun getTokenData(): LoginResponse? {
        return try {
            val tokenJson = encryptedSharedPreferences.getString(TOKEN_DATA_KEY, null)
            tokenJson?.let { gson.fromJson(it, LoginResponse::class.java) }
        } catch (e: JsonSyntaxException) {
            null // Si hay error en deserialización, retorna null
        } catch (e: Exception) {
            null
        }
    }
}