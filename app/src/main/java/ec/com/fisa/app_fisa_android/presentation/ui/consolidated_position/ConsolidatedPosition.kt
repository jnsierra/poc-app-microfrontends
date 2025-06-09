package ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.components.FlutterBox
import ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.components.FlutterBoxWithCommunication
import io.flutter.embedding.android.FlutterActivity

@Composable
fun ConsolidatedPosition(
    navController: NavController,
    consolidatedPositionViewModel: ConsolidatedPositionViewModel
) {
    val tokenStr: String by consolidatedPositionViewModel.token.observeAsState(initial = "");
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 80.dp)
            .background(Color.Red)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    consolidatedPositionViewModel.onLoginSelected()
                }) {
                Text("Traer login")
            }
            Text(
                "Este es el token: $tokenStr",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.width(300.dp)
            )
//            FlutterBox(
//                modifier = Modifier.fillMaxWidth(),
//                height = 350.dp,
//                title = "Análisis Gráfico",
//                initialRoute = "/position-charts",
//                showCard = true,
//                onError = {
//                    // Manejar errores de Flutter
//                    //showFlutterAnalytics = false
//                    Log.i("Error", "Error al cargar la vista de Flutter")
//                }
//            )
            FlutterBoxWithCommunication(
                modifier = Modifier.fillMaxWidth(),
                height = 350.dp,
                title = "Análisis Gráfico",
                initialRoute = "/",
                showCard = true,
            )
        }
    }
}


