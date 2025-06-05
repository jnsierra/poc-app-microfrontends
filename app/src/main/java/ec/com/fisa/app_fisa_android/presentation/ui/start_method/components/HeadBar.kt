package ec.com.fisa.app_fisa_android.presentation.ui.start_method.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ec.com.fisa.app_fisa_android.R

@Composable
fun HeadBar(modifier :Modifier) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ).align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.girl_pay_app),
                contentDescription = null,
                modifier = modifier
                    .width(248.10.dp)
                    .height(247.91.dp)
            )
        }
        Spacer(modifier = Modifier.height(9.dp))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.lock_outline),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = colorResource(id = R.color.gray_400)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "TRANSACCIONES SEGURAS",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 8.sp,
                    color = colorResource(R.color.gray_400)
                )
            )
        }
    }
}