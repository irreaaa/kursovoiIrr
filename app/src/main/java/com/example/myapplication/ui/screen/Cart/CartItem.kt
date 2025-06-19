package com.example.myapplication.ui.screen.Cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.remote.network.response.SneakersResponse
import com.example.myapplication.ui.theme.MatuleTheme
import kotlin.math.abs

@Composable
fun CartItemCard(
    sneaker: SneakersResponse,
    count: Int,
    modifier: Modifier = Modifier,
    onQuantityChanged: (Int, Int) -> Unit,
    onDeleteClick: (Int) -> Unit
){
    val context = LocalContext.current
    val imageRes = context.resources.getIdentifier(sneaker.imageUrl, "drawable", context.packageName)

    var offsetX by remember { mutableStateOf(0f) }
    var count by remember { mutableStateOf(1) }
    var localCount by remember { mutableStateOf(count) }

    LaunchedEffect(key1 = count) {
        localCount = count
    }

    fun updateCount(newCount: Int) {
        if (newCount >= 1) {
            localCount = newCount
            onQuantityChanged(sneaker.id, newCount)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX < -230f) {
                            onDeleteClick(sneaker.id)
                        } else {
                            offsetX = 0f
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount
                    offsetX = offsetX.coerceIn(-500f, 0f)
                }
            }
    ) {
        if (abs(offsetX) > 30f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xFFd4040e), RoundedCornerShape(12.dp))
                    .padding(end = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text("Удалить", color = Color.White, fontSize = 15.sp)
            }
        }

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFFFE5F2),
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), 0) }
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(99.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(22.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = sneaker.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "₽${sneaker.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        color = MatuleTheme.colors.accent
                    )
                }

//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    IconButton(onClick = { updateCount(count + 1) }) {
//                        Icon(painterResource(R.drawable.ic_minus), contentDescription = "+")
//                    }
//                    Text("$count", fontSize = 18.sp, color = MatuleTheme.colors.accent)
//                    IconButton(onClick = { updateCount(count - 1) }) {
//                        Icon(painterResource(R.drawable.ic_plus), contentDescription = "-")
//                    }
//                }
            }
        }
    }
}



//colors = listOf(Color(0xFFC189A5), Color(0xFFFFE5F2))