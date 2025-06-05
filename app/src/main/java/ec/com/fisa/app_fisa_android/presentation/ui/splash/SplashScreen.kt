package ec.com.fisa.app_fisa_android.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import ec.com.fisa.app_fisa_android.R


@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val primary500 = Color(ContextCompat.getColor(context, R.color.primary_500))
    val primary600 = Color(ContextCompat.getColor(context, R.color.primary_600))
    val primary700 = Color(ContextCompat.getColor(context, R.color.primary_700))
    val primary800 = Color(ContextCompat.getColor(context, R.color.primary_800))
    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        primary500,
                        primary600,
                        primary700,
                        primary800
                    )
                )
            )
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.launch_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .width(153.dp)
                .height(58.dp)
                .align(Alignment.Center)
        )
    }
}
