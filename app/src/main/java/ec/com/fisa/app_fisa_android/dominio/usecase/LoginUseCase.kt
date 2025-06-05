package ec.com.fisa.app_fisa_android.dominio.usecase

import ec.com.fisa.app_fisa_android.data.repository.LoginRepository
import ec.com.fisa.app_fisa_android.dominio.modelo.LoginResponseEnum
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(user: String, password: String): LoginResponseEnum {
        return repository.doLogin(user = user, password = password)
    }
}