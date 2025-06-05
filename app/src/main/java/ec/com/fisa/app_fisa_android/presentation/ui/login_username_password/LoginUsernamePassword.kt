package ec.com.fisa.app_fisa_android.presentation.ui.login_username_password

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ec.com.fisa.app_fisa_android.R
import ec.com.fisa.app_fisa_android.dominio.modelo.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUsernamePassword(
    navController: NavController,
    loginUsernamePasswordViewModel: LoginUsernamePasswordViewModel
) {
    val navigateTo by loginUsernamePasswordViewModel.navigateTo.observeAsState()

    LaunchedEffect(navigateTo) {
        navigateTo?.let { route ->
            navController.navigate(route.route)
            loginUsernamePasswordViewModel.onNavigationHandled() // Reset navigation state
        }
    }

    val isEnableLogin:Boolean by loginUsernamePasswordViewModel.isEnableLogin.observeAsState(initial = true)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Box(modifier = Modifier.fillMaxHeight()) {
                            Icon(
                                painter = painterResource(id = R.drawable.logo_start),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Fisa Bank",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.StartMethod.route)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Permite scroll
                    .imePadding() // Padding cuando aparece el teclado
            ) {
                // Formulario de login
                _formLogin(
                    modifier = Modifier,
                    loginUsernamePaswordViewModel = loginUsernamePasswordViewModel
                )

                // Espaciador flexible que empuja los botones hacia abajo
                Spacer(modifier = Modifier.weight(1f))

                // Botones al final
                _buttonLogin(
                    modifier = Modifier,
                    loginUsernamePasswordViewModel = loginUsernamePasswordViewModel,
                    loginEnable = isEnableLogin
                )
            }
        }
    }
}

@Composable
fun _buttonLogin(
    modifier: Modifier,
    loginUsernamePasswordViewModel: LoginUsernamePasswordViewModel,
    loginEnable: Boolean,
) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Button(
            enabled = loginEnable,
            onClick = {
                loginUsernamePasswordViewModel.onLoginSelected()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary_500)
            )
        ) {
            Text("Iniciar sesión", fontSize = 17.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
            ),
            border = BorderStroke(
                width = 2.dp,
                color = colorResource(R.color.primary_500)
            )
        ) {
            Text("Crear una cuenta", color = colorResource(R.color.primary_500), fontSize = 17.sp)
        }
    }
}

@Composable
fun _formLogin(modifier: Modifier, loginUsernamePaswordViewModel: LoginUsernamePasswordViewModel) {
    val username: String by loginUsernamePaswordViewModel.username.observeAsState(initial = "QAfisa2024")
    val password: String by loginUsernamePaswordViewModel.password.observeAsState(initial = "Fisaprod2024")

    Column(
        modifier = modifier
            .padding(24.dp)
    ) {
        _userField(username) {
            loginUsernamePaswordViewModel.onLoginChanged(username = it, password = password)
        }
        _passwordField(password) {
            loginUsernamePaswordViewModel.onLoginChanged(username = username, password = it)
        }
    }
}

@Composable
fun _userField(user: String, onTextChanged: (String) -> Unit) {
    Column {
        Text("Usuario", fontWeight = FontWeight.W700, fontSize = 14.sp)
        Spacer(modifier = Modifier.size(4.dp))
        TextField(
            value = user,
            onValueChange = { onTextChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.gray_300),
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            placeholder = {
                Text("Escribe tu usuario", color = colorResource(R.color.gray_300))
            },
            leadingIcon = {
                Icon(
                    Icons.Sharp.Person,
                    contentDescription = null,
                    tint = colorResource(R.color.gray_400)
                )
            }
        )
    }
}

@Composable
fun _passwordField(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    Column {
        Text("Contraseña", fontWeight = FontWeight.W700, fontSize = 14.sp)
        Spacer(modifier = Modifier.size(4.dp))
        TextField(
            value = password,
            onValueChange = { onTextChanged(it) },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.gray_300),
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            placeholder = {
                Text("Contraseña", color = colorResource(R.color.gray_300))
            },
            leadingIcon = {
                Icon(
                    Icons.Sharp.Lock,
                    contentDescription = null,
                    tint = colorResource(R.color.gray_400)
                )
            },
            trailingIcon = {
                val imagen = if (passwordVisibility) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility }
                ) {
                    Icon(
                        imagen,
                        contentDescription = null,
                        tint = colorResource(R.color.gray_400)
                    )
                }
            }
        )
    }
}