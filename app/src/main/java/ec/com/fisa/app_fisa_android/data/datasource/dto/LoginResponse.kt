package ec.com.fisa.app_fisa_android.data.datasource.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("refresh_expires_in") val refreshExpiresIn: Int,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("token_type") val tokenType: String,
) {

    fun isSuccess(): Boolean {
        return accessToken.isNotEmpty()
    }
}
