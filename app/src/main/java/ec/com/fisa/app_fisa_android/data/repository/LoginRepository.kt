package ec.com.fisa.app_fisa_android.data.repository

import ec.com.fisa.app_fisa_android.data.datasource.dto.LoginResponse
import ec.com.fisa.app_fisa_android.data.service.LoginService
import ec.com.fisa.app_fisa_android.data.service.SecureTokenManager
import ec.com.fisa.app_fisa_android.dominio.modelo.LoginResponseEnum
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginService: LoginService,
    private val secureTokenManager: SecureTokenManager
) {

    suspend fun doLogin(user: String, password: String): LoginResponseEnum {
        val response: LoginResponse = this.loginService.doLogin(user, password)
            ?: return LoginResponseEnum.errorUserOrPassword

        //if (response.accessToken.isNullOrEmpty()) {
        secureTokenManager.saveTokenData(response)
        //}

        return LoginResponseEnum.success
    }
}