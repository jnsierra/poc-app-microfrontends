package ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ec.com.fisa.app_fisa_android.data.datasource.dto.LoginResponse
import ec.com.fisa.app_fisa_android.data.service.SecureTokenManager
import javax.inject.Inject

@HiltViewModel
class ConsolidatedPositionViewModel @Inject constructor(private val secureTokenManager: SecureTokenManager) :
    ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun onLoginSelected(){
        val response:LoginResponse? = secureTokenManager.getTokenData();
        if (response != null) {
            Log.i("Token","Token: ${response.accessToken}")
            _token.value = response.accessToken
        }
    }

    init {
        val response:LoginResponse? = secureTokenManager.getTokenData();
        if (response != null) {
            Log.i("Token","Token: ${response.accessToken}")
        }

    }

}