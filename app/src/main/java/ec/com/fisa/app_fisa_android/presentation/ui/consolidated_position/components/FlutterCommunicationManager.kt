package ec.com.fisa.app_fisa_android.presentation.ui.consolidated_position.components

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class FlutterCommunicationManager(private val flutterEngine: FlutterEngine) {

    companion object {
        private const val METHOD_CHANNEL = "ec.com.fisa/method_channel"
        private const val EVENT_CHANNEL = "ec.com.fisa/method_channel"
        private const val USER_DATA_CHANNEL = "ec.com.fisa/method_channel"
        private const val NAVIGATION_CHANNEL = "ec.com.fisa/method_channel"
    }

    private var methodChannel: MethodChannel? = null
    private var eventChannel: EventChannel? = null
    private var userDataChannel: MethodChannel? = null
    private var navigationChannel: MethodChannel? = null
    private var eventSink: EventChannel.EventSink? = null

    // Callbacks para recibir datos desde Flutter
    var onUserDataReceived: ((Map<String, Any>) -> Unit)? = null
    var onNavigationRequested: ((String, Map<String, Any>?) -> Unit)? = null
    var onFlutterMethodCall: ((String, Map<String, Any>?) -> Any?)? = null

    init {
        setupChannels()
    }

    private fun setupChannels() {
        // Canal de métodos principal
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, METHOD_CHANNEL)
        methodChannel?.setMethodCallHandler { call, result ->
            handleMethodCall(call.method, call.arguments as? Map<String, Any>, result)
        }

        // Canal de eventos (Android -> Flutter)
        eventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, EVENT_CHANNEL)
        eventChannel?.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                eventSink = events
            }

            override fun onCancel(arguments: Any?) {
                eventSink = null
            }
        })

        // Canal específico para datos de usuario
        userDataChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, USER_DATA_CHANNEL)
        userDataChannel?.setMethodCallHandler { call, result ->
            when (call.method) {
                "sendUserData" -> {
                    val userData = call.arguments as? Map<String, Any>
                    userData?.let { onUserDataReceived?.invoke(it) }
                    result.success("Data received successfully")
                }
                "getUserInfo" -> {
                    val userInfo = getCurrentUserInfo()
                    result.success(userInfo)
                }
                else -> result.notImplemented()
            }
        }

        // Canal de navegación
        navigationChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, NAVIGATION_CHANNEL)
        navigationChannel?.setMethodCallHandler { call, result ->
            when (call.method) {
                "navigateToNative" -> {
                    val route = call.argument<String>("route")
                    val params = call.argument<Map<String, Any>>("params")
                    route?.let { onNavigationRequested?.invoke(it, params) }
                    result.success("Navigation handled")
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun handleMethodCall(
        method: String,
        arguments: Map<String, Any>?,
        result: MethodChannel.Result
    ) {
        val response = onFlutterMethodCall?.invoke(method, arguments)
        if (response != null) {
            result.success(response)
        } else {
            when (method) {
                "getDeviceInfo" -> {
                    result.success(getDeviceInfo())
                }
                "getAppVersion" -> {
                    result.success(getAppVersion())
                }
                "showNativeDialog" -> {
                    val title = arguments?.get("title") as? String ?: "Info"
                    val message = arguments?.get("message") as? String ?: ""
                    showNativeDialog(title, message)
                    result.success("Dialog shown")
                }
                else -> result.notImplemented()
            }
        }
    }

    // === MÉTODOS PARA ENVIAR DATOS A FLUTTER ===

    fun sendEventToFlutter(eventType: String, data: Map<String, Any>) {
        val eventData = mapOf(
            "type" to eventType,
            "data" to data,
            "timestamp" to System.currentTimeMillis()
        )

        CoroutineScope(Dispatchers.Main).launch {
            eventSink?.success(eventData)
        }
    }

    fun sendUserDataToFlutter(userData: Map<String, Any>) {
        userDataChannel?.invokeMethod("updateUserData", userData)
    }

    fun sendAccountBalanceToFlutter(balance: Double, accountType: String) {
        val balanceData = mapOf(
            "balance" to balance,
            "accountType" to accountType,
            "currency" to "USD",
            "lastUpdated" to System.currentTimeMillis()
        )
        sendEventToFlutter("account_balance_updated", balanceData)
    }

    fun sendTransactionToFlutter(transaction: Map<String, Any>) {
        sendEventToFlutter("new_transaction", transaction)
    }

    fun notifyFlutterOfNetworkChange(isConnected: Boolean) {
        val networkData = mapOf("isConnected" to isConnected)
        sendEventToFlutter("network_status_changed", networkData)
    }

    // === MÉTODOS PARA LLAMAR FUNCIONES DE FLUTTER ===

    fun callFlutterMethod(method: String, arguments: Map<String, Any>? = null, callback: ((Any?) -> Unit)? = null) {
        methodChannel?.invokeMethod(method, arguments, object : MethodChannel.Result {
            override fun success(result: Any?) {
                callback?.invoke(result)
            }

            override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
                println("Flutter method error: $errorCode - $errorMessage")
                callback?.invoke(null)
            }

            override fun notImplemented() {
                println("Flutter method not implemented: $method")
                callback?.invoke(null)
            }
        })
    }

    fun requestDataFromFlutter(dataType: String, callback: (Map<String, Any>?) -> Unit) {
        callFlutterMethod("getData", mapOf("type" to dataType)) { result ->
            callback(result as? Map<String, Any>)
        }
    }

    fun triggerFlutterAction(action: String, params: Map<String, Any> = emptyMap()) {
        callFlutterMethod("triggerAction", mapOf("action" to action, "params" to params))
    }

    // === MÉTODOS AUXILIARES ===

    private fun getCurrentUserInfo(): Map<String, Any> {
        return mapOf(
            "userId" to "12345",
            "name" to "Usuario FISA",
            "email" to "usuario@fisa.com",
            "accountType" to "premium",
            "lastLogin" to System.currentTimeMillis()
        )
    }

    private fun getDeviceInfo(): Map<String, Any> {
        return mapOf(
            "platform" to "Android",
            "version" to android.os.Build.VERSION.RELEASE,
            "model" to android.os.Build.MODEL,
            "manufacturer" to android.os.Build.MANUFACTURER
        )
    }

    private fun getAppVersion(): String {
        return "1.0.0" // Obtener de PackageManager
    }

    private fun showNativeDialog(title: String, message: String) {
        // Implementar diálogo nativo
    }

    fun cleanup() {
        methodChannel?.setMethodCallHandler(null)
        eventChannel?.setStreamHandler(null)
        userDataChannel?.setMethodCallHandler(null)
        navigationChannel?.setMethodCallHandler(null)
        eventSink = null
    }
}