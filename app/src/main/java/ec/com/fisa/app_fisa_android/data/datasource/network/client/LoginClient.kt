package ec.com.fisa.app_fisa_android.data.datasource.network.client

import ec.com.fisa.app_fisa_android.data.datasource.dto.LoginResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginClient {

    @POST("/realms/{realm}/protocol/openid-connect/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun doLogin(
        @Path("realm") realm: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ):Response<LoginResponse>

}