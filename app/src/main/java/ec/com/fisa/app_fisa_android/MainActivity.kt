package ec.com.fisa.app_fisa_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import ec.com.fisa.app_fisa_android.core.NavigationScreen
import ec.com.fisa.app_fisa_android.presentation.theme.App_fisa_androidTheme
import ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.ConsolidatedPositionViewModel
import ec.com.fisa.app_fisa_android.presentation.ui.login_username_password.LoginUsernamePasswordViewModel
import io.flutter.embedding.android.FlutterFragment

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val loginUsernamePasswordViewModel: LoginUsernamePasswordViewModel by viewModels()
    private val consolidatedPositionViewModel: ConsolidatedPositionViewModel by viewModels()

    // Flutter fragment management
    companion object {
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    }

    private var flutterFragment: FlutterFragment? = null

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

    /**
     * Función para crear FlutterFragment con configuraciones personalizadas
     * Llamar desde tus Composables cuando necesites mostrar Flutter
     */
    fun createFlutterFragment(
        initialRoute: String = "/",
        useTransparency: Boolean = true
    ): FlutterFragment {
        return FlutterFragment.withCachedEngine(AppFisaComposeApp.CACHED_ENGINE_ID)
            .apply {
                if (useTransparency) {
                    //transparencyMode(FlutterView.TransparencyMode.transparent)

                }
            }
            .shouldAttachEngineToActivity(false) // No controlar la Activity
            .build()
    }

    override fun onPostResume() {
        super.onPostResume()
        flutterFragment?.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        flutterFragment?.onNewIntent(intent)
    }

//    override fun onBackPressed() {
//        // Permitir que Flutter maneje el back press primero
//        if (flutterFragment?.onBackPressed() != true) {
//            super.onBackPressed()
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        flutterFragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        flutterFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        flutterFragment?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        flutterFragment?.onTrimMemory(level)
    }

    /**
     * Método para registrar un FlutterFragment activo
     * Lo llama automáticamente el FlutterContainer
     */
    fun registerFlutterFragment(fragment: FlutterFragment) {
        this.flutterFragment = fragment
    }

    /**
     * Método para limpiar la referencia del FlutterFragment
     */
    fun unregisterFlutterFragment() {
        this.flutterFragment = null
    }
}