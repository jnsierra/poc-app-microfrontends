package ec.com.fisa.app_fisa_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

@HiltAndroidApp
class AppFisaComposeApp: Application() {

    companion object{
        const val CACHED_ENGINE_ID = "fisa_flutter_engine"
    }

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


}