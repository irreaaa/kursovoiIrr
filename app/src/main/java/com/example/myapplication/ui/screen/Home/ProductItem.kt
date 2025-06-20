package com.example.myapplication.ui.screen.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.remote.network.response.SneakersResponse
import com.example.myapplication.ui.theme.MatuleTheme

@Composable
fun getDrawableId(imageName: String): Int {
    val context = LocalContext.current
    val id = context.resources.getIdentifier(imageName, "drawable", context.packageName)
    return id
}

@Composable
fun ProductItem(
    sneaker: SneakersResponse,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onAddToCart: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageRes = getDrawableId(sneaker.imageUrl)

    Column(
        modifier = modifier
            .width(230.dp)
            .height(300.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Brush.linearGradient(listOf(Color(0xFFC189A5), Color(0xFFFFE5F2)))),
        //ffb6c1 FFE4E1
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1.6f)
                //.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )

            IconButton(
                onClick = {
                    onFavoriteClick(sneaker.id, !sneaker.isFavorite)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(1.dp)
            ) {
                Image(
                    painter = painterResource(
                        if (sneaker.isFavorite) R.drawable.new_heart else R.drawable.new_empty_heart
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = sneaker.category.uppercase(),
                color = MatuleTheme.colors.accent,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = sneaker.name,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 17.sp
                ),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp),
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₽${"%.2f".format(sneaker.price)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }


        IconButton(
            onClick = { onAddToCart(sneaker.id, !sneaker.inCart) },
            modifier = Modifier
                .align(Alignment.End)
                .size(50.dp)
        ) {
            Image(
                painter = painterResource(
                    if (sneaker.inCart) R.drawable.done else R.drawable.add_b
                ),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}