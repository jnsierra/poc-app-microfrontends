package ec.com.fisa.app_fisa_android.presentation.ui.start_method.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ec.com.fisa.app_fisa_android.R
import ec.com.fisa.app_fisa_android.dominio.modelo.LoginTypeEnum

@Composable
fun BodyBar(
    onLoginClick: (LoginTypeEnum) -> Unit,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onLoginClick(LoginTypeEnum.USERNAME_PASSWORD)
                    }

            ) {
                CardButton(
                    iconRes = R.drawable.passkey,
                    text = "Usuario y contraseña"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier.weight(1f).clickable {
                    onLoginClick(LoginTypeEnum.FACE_ID)
                }
            ) {
                CardButton(
                    iconRes = R.drawable.fingerprint,
                    text = "Huella o Face ID"
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalDivider(color = colorResource( id = R.color.gray_100), thickness = 1.dp, modifier = Modifier.padding(start = 15.dp, end = 15.dp))

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Conoce nuestras sucursales",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.primary_400)
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                Icons.Outlined.PinDrop,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Versión 1.0.0",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.primary_200)
            )
        )
    }
}

@Composable
private fun CardButton(iconRes: Int, text: String) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp).align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = text, fontSize = 14.sp, textAlign = TextAlign.Center)
        }
    }
}