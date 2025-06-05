package ec.com.fisa.app_fisa_android.presentation.ui.start_method

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ec.com.fisa.app_fisa_android.dominio.modelo.LoginTypeEnum
import ec.com.fisa.app_fisa_android.dominio.modelo.Routes
import ec.com.fisa.app_fisa_android.presentation.ui.start_method.components.BodyBar
import ec.com.fisa.app_fisa_android.presentation.ui.start_method.components.HeadBar
import ec.com.fisa.app_fisa_android.presentation.ui.start_method.components.LogoAppBar

@Composable
fun StartMethod(navController: NavController){
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).background(Color.White).fillMaxSize()){
            Column {
                LogoAppBar()
                Spacer(modifier = Modifier.weight(1f))
                HeadBar(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.weight(1f))
                BodyBar(){ it -> onLoginClick(it, navController)}
                Spacer(modifier = Modifier.height(49.09.dp))

            }
        }

    }
}

fun onLoginClick(loginTypeEnum: LoginTypeEnum, navController: NavController ){
    when (loginTypeEnum){
        LoginTypeEnum.USERNAME_PASSWORD -> navController.navigate(Routes.LoginUsernamePassword.route)
        LoginTypeEnum.FACE_ID -> TODO()
    }
}



