package ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

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
            Text("Esta es la posicion consolidada", textAlign = TextAlign.Center)
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
            Button(
                onClick = {
                    context.startActivity(
                        FlutterActivity.createDefaultIntent(context)
                    )
                }) {
                Text("Traer app flutter")
            }
            FlutterModuleInCompose()
        }
    }
}


@Composable
fun FlutterModuleInCompose() {
    Box(
        modifier = Modifier
            .background(Color.Gray)
    ) {
        AndroidView(
            factory = { context ->
                // Crear el FlutterView
                val flutterEngine = FlutterEngine(context)

                // Configurar la ruta inicial del módulo Flutter
                flutterEngine.navigationChannel.setInitialRoute("/")

                // Ejecutar el bundle de Dart
                flutterEngine.dartExecutor.executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
                )

                // Crear y retornar el FlutterView
                FlutterView(context).apply {
                    attachToFlutterEngine(flutterEngine)
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { flutterView ->
                // Actualizaciones si es necesario
            }
        )
    }
}
