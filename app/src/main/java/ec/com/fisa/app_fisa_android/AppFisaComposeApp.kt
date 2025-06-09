package ec.com.fisa.app_fisa_android

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.components.FlutterCommunicationManager
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import kotlin.math.log

@HiltAndroidApp
class AppFisaComposeApp: Application() {

    companion object{
        const val CACHED_ENGINE_ID = "fisa_flutter_engine"
    }

    private var communicationManager: FlutterCommunicationManager? = null

    override fun onCreate() {
        super.onCreate()
        // AGREGAR AL FINAL:
        initializeFlutterEngine()
    }

    private fun initializeFlutterEngine() {
        try {
            // Crear FlutterEngine precalentado
            val flutterEngine = FlutterEngine(this)

            // Configurar ruta inicial
            flutterEngine.navigationChannel.setInitialRoute("/")

            // Ejecutar Dart
            flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )
            // Configurar comunicación bidireccional
            communicationManager = FlutterCommunicationManager(flutterEngine).apply {
                // Configurar callbacks para recibir datos de Flutter
                onUserDataReceived = { userData ->
                    handleUserDataFromFlutter(userData)
                }

                onNavigationRequested = { route, params ->
                    handleNavigationFromFlutter(route, params)
                }

                onFlutterMethodCall = { method, args ->
                    handleCustomMethodCall(method, args)
                }
            }

            // Cachear el engine para uso posterior
            FlutterEngineCache
                .getInstance()
                .put(CACHED_ENGINE_ID, flutterEngine)

        } catch (e: Exception) {
            // Manejar errores de inicialización de Flutter
            // No afecta el funcionamiento normal de la app si Flutter no está disponible
            e.printStackTrace()
        }
    }

    private fun handleUserDataFromFlutter(userData: Map<String, Any>) {
        // Procesar datos recibidos de Flutter
        println("Datos recibidos de Flutter: $userData")
        // Aquí puedes actualizar tu base de datos, SharedPreferences, etc.
    }

    private fun handleNavigationFromFlutter(route: String, params: Map<String, Any>?) {
        // Manejar navegación solicitada por Flutter
        println("Flutter solicita navegación a: $route con params: $params")
        // Implementar lógica de navegación nativa
    }

    private fun handleCustomMethodCall(method: String, args: Map<String, Any>?): Any? {
        return when (method) {
            "getAccountBalance" -> {
                // Retornar balance de cuenta desde Android
                mapOf(
                    "balance" to 1500.75,
                    "currency" to "USD"
                )
            }
            "makePayment" -> {
                // Procesar pago desde Flutter
                val amount = args?.get("amount") as? Double
                val recipient = args?.get("recipient") as? String
                processPayment(amount, recipient)
            }
            else -> null
        }
    }

    private fun processPayment(amount: Double?, recipient: String?): Map<String, Any> {
        // Simular procesamiento de pago
        return mapOf(
            "success" to true,
            "transactionId" to "TXN_${System.currentTimeMillis()}",
            "amount" to (amount ?: 0.0),
            "recipient" to (recipient ?: "")
        )
    }

    fun getCommunicationManager(): FlutterCommunicationManager? = communicationManager


}