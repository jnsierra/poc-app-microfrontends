package ec.com.fisa.app_fisa_android.presentation.ui.login_username_password

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ec.com.fisa.app_fisa_android.dominio.modelo.LoginResponseEnum
import ec.com.fisa.app_fisa_android.dominio.modelo.Routes
import ec.com.fisa.app_fisa_android.dominio.usecase.LoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginUsernamePasswordViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isEnableLogin = MutableLiveData<Boolean>()
    val isEnableLogin: LiveData<Boolean> = _isEnableLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _navigateTo = MutableLiveData<Routes?>()
    val navigateTo: LiveData<Routes?> = _navigateTo

    init {
        _username.value = "QAfisa2024"
        _password.value = "Fisaprod2024"
    }

    fun onLoginChanged(username: String, password: String){
        _username.value = username
        _password.value = password
        _isEnableLogin.value = enableLogin(username, password)
    }

    private fun enableLogin(username: String, password: String): Boolean {
        return username.length > 5 && password.length > 5
    }

    fun onLoginSelected(){
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000)
            val result: LoginResponseEnum = loginUseCase(username.value!!, password.value!!)
            if(result == LoginResponseEnum.success) {
                Log.i("Login", "Login successful")
                // Navigate to the main screen or next screen
                _navigateTo.value = Routes.ConsolidatedPosition
                //navController.navigate(Routes.ConsolidatedPosition.route)
            } else {
                Log.e("Login", "Login failed: $result")
                // Handle login failure (e.g., show error message)

            }
            _isLoading.value = false
        }
    }

    fun onNavigationHandled() {
        _navigateTo.value = null
    }

}