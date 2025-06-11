package com.example.myapplication.ui.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopPanel(title: String,
             menuImage: Painter,
             basketImage: Painter,
             textSize: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(top = 40.dp, start = 16.dp)
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterStart)
                .padding(end = 16.dp)
        ) {
            Icon(
                painter = menuImage,
                contentDescription = null
            )
        }

        Text(text = title,
            fontWeight = FontWeight.Medium,
            fontSize = textSize.sp,
            modifier = Modifier.align(Alignment.Center),
            color = Color(0xFF660033)
        )

        IconButton(
            onClick = {},
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 10.dp)
        ) {
            Image(
                painter = basketImage,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}