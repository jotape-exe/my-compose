package com.joaoxstone.jxscompose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun JXSButton(onClick: () -> Unit, message: String = "", icon: ImageVector? = null, modifier: Modifier = Modifier) {
    Button(
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp, pressedElevation = 8.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
            contentColor = if(isSystemInDarkTheme()) Color.Black else Color.White
        )
    ) {
        if(message.isNotEmpty()){
            Text(text = message)
        }
        icon?.let{
            Icon(it, contentDescription = "next")
        }

    }
}
