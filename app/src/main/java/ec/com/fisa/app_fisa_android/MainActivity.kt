package ec.com.fisa.app_fisa_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import ec.com.fisa.app_fisa_android.core.NavigationScreen
import ec.com.fisa.app_fisa_android.presentation.theme.App_fisa_androidTheme
import ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.ConsolidatedPositionViewModel
import ec.com.fisa.app_fisa_android.presentation.ui.login_username_password.LoginUsernamePasswordViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginUsernamePasswordViewModel: LoginUsernamePasswordViewModel by viewModels()
    private val consolidatedPositionViewModel: ConsolidatedPositionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_fisa_androidTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavigationScreen(loginUsernamePasswordViewModel, consolidatedPositionViewModel)
                }
            }
        }
    }
}