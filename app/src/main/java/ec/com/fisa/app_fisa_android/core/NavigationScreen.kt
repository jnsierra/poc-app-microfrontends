package ec.com.fisa.app_fisa_android.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ec.com.fisa.app_fisa_android.dominio.modelo.Routes
import ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.ConsolidatedPosition
import ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.ConsolidatedPositionViewModel
import ec.com.fisa.app_fisa_android.presentation.ui.login_username_password.LoginUsernamePassword
import ec.com.fisa.app_fisa_android.presentation.ui.login_username_password.LoginUsernamePasswordViewModel
import ec.com.fisa.app_fisa_android.presentation.ui.main.MainScreen
import ec.com.fisa.app_fisa_android.presentation.ui.splash.SplashScreen
import ec.com.fisa.app_fisa_android.presentation.ui.start_method.StartMethod

@Composable
fun NavigationScreen(
    loginUsernamePasswordViewModel: LoginUsernamePasswordViewModel,
    consolidatedPositionViewModel: ConsolidatedPositionViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreenDest.route
    ) {
        composable(route = Routes.SplashScreenDest.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Routes.MainScreenDest.route) {
            MainScreen()
        }

        composable(route = Routes.StartMethod.route) {
            StartMethod(navController = navController)
        }

        composable(route = Routes.LoginUsernamePassword.route) {
            LoginUsernamePassword(
                navController = navController,
                loginUsernamePasswordViewModel = loginUsernamePasswordViewModel
            )
        }

        composable(route = Routes.ConsolidatedPosition.route) {
            ConsolidatedPosition(
                navController = navController,
                consolidatedPositionViewModel = consolidatedPositionViewModel
            )
        }
    }
}