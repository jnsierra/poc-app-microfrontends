package ec.com.fisa.app_fisa_android.dominio.modelo

sealed class Routes(val route:String) {
    object SplashScreenDest: Routes("splash")
    object MainScreenDest: Routes("main_screen_dest")
    object StartMethod: Routes("start_method")
    object LoginUsernamePassword: Routes("login_username_password")
    object ConsolidatedPosition: Routes("consolidated_position")
}