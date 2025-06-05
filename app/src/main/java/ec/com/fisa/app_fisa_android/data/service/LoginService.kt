package ec.com.fisa.app_fisa_android.data.service

import ec.com.fisa.app_fisa_android.data.datasource.dto.LoginResponse
import ec.com.fisa.app_fisa_android.data.datasource.network.client.LoginClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient) {

    suspend fun doLogin(user: String, password: String): LoginResponse? {
        return withContext(Dispatchers.IO) {
            val realm = "wso2"
            val grantType = "password"
            val basicAuth =
                "Basic NjUxOTJlOTQtNjAzOS00YzBhLWJjY2MtOWEyNDc4NTg5YjY3Om5jU2p6Y0hCUkxXeE1ZUDlUVzlPM0Nxa2dMdHRwalZr"

            // Crear el body exactamente como en tu request
            val data = "grant_type=$grantType&username=$user&password=$password"
            val requestBody =
                data.toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())

            val response = loginClient.doLogin(
                realm = realm,
                authorization = basicAuth,
                requestBody = requestBody
            )
            if(response.isSuccessful && response.body() != null){
                response.body()
            }else{
                null
            }
        }
    }
}