package ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import ec.com.fisa.app_fisa_android.AppFisaComposeApp
import ec.com.fisa.app_fisa_android.MainActivity

@Composable
fun FlutterBox(
    modifier: Modifier = Modifier,
    height: Dp = 400.dp,
    initialRoute: String = "/",
    showCard: Boolean = true,
    title: String? = null,
    onError: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val activity = context as? MainActivity

    if (showCard) {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column {
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                FlutterContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .padding(if (title != null) 16.dp else 0.dp),
                    initialRoute = initialRoute,
                    activity = activity,
                    onError = onError
                )
            }
        }
    } else {
        FlutterContainer(
            modifier = modifier.height(height),
            initialRoute = initialRoute,
            activity = activity,
            onError = onError
        )
    }
}

@Composable
private fun FlutterContainer(
    modifier: Modifier = Modifier,
    initialRoute: String = "/",
    activity: MainActivity?,
    onError: (() -> Unit)? = null
) {
    var hasError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (hasError) {
            // Estado de error
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Error al cargar Flutter",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
                if (onError != null) {
                    Button(
                        onClick = {
                            hasError = false
                            onError.invoke()
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        } else if (activity != null) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    FragmentContainerView(ctx).apply {
                        id = android.R.id.content + hashCode() // ID único para cada instancia

                        try {
                            // Crear el FlutterFragment con la configuración especificada
                            val fragment = activity.createFlutterFragment(
                                initialRoute = initialRoute,
                                useTransparency = true
                            )

                            // Registrar el fragment en la Activity para manejo del ciclo de vida
                            activity.registerFlutterFragment(fragment)

                            // Agregar el fragment al contenedor
                            activity.supportFragmentManager
                                .beginTransaction()
                                .replace(this.id, fragment)
                                .commit()

                        } catch (e: Exception) {
                            e.printStackTrace()
                            hasError = true
                        }
                    }
                },
                onRelease = {
                    // Limpiar la referencia cuando se destruye el Composable
                    activity.unregisterFlutterFragment()
                }
            )
        } else {
            // Fallback si no se puede obtener la MainActivity
            Text(
                text = "Error: No se pudo inicializar Flutter",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

// Composable simplificado para casos de uso básicos
@Composable
fun SimpleFlutterModule(
    modifier: Modifier = Modifier,
    route: String = "/",
    height: Dp = 300.dp
) {
    FlutterBox(
        modifier = modifier,
        height = height,
        initialRoute = route,
        showCard = false
    )
}

// 4. Ejemplo de uso en una de tus pantallas existentes
@Composable
fun FlutterIntegrationExample() {
    var showFlutter by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { showFlutter = !showFlutter },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (showFlutter) "Ocultar Flutter" else "Mostrar Flutter")
        }

        if (showFlutter) {
            FlutterBox(
                modifier = Modifier.fillMaxWidth(),
                height = 300.dp,
                title = "Módulo Flutter FISA",
                initialRoute = "/"
            )
        }

        // Resto de tu contenido existente
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Contenido nativo de FISA",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}



@Composable
fun FlutterBoxWithCommunication(
    modifier: Modifier = Modifier,
    height: Dp = 400.dp,
    initialRoute: String = "/",
    initialData: Map<String, Any>? = null,
    onDataReceived: ((Map<String, Any>) -> Unit)? = null,
    showCard: Boolean = true,
    title: String? = null
) {
    val context = LocalContext.current
    val activity = context as? MainActivity
    val app = context.applicationContext as? AppFisaComposeApp
    val communicationManager = app?.getCommunicationManager()

    // Configurar callbacks de comunicación
    LaunchedEffect(onDataReceived) {
        communicationManager?.onUserDataReceived = onDataReceived
    }

    // Enviar datos iniciales cuando se monta el componente
    LaunchedEffect(initialData) {
        initialData?.let { data ->
            communicationManager?.sendEventToFlutter("initial_data", data)
        }
    }

    if (showCard) {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column {
                if (title != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall
                        )

                        // Botón para enviar datos de prueba a Flutter
                        Button(
                            onClick = {
                                communicationManager?.sendEventToFlutter(
                                    "test_data",
                                    mapOf(
                                        "message" to "Hola desde Android",
                                        "timestamp" to System.currentTimeMillis()
                                    )
                                )
                            },
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("📤")
                        }
                    }
                }

                FlutterContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .padding(if (title != null) 16.dp else 0.dp),
                    initialRoute = initialRoute,
                    activity = activity
                )
            }
        }
    } else {
        FlutterContainer(
            modifier = modifier.height(height),
            initialRoute = initialRoute,
            activity = activity
        )
    }
}
