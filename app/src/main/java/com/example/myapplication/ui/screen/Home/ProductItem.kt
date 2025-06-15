package com.example.myapplication.ui.screen.Home

import android.util.Log
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
    Log.d("ProductItem", "Image name: $imageName, resolved id: $id")
    return id
}

@Composable
fun ProductItem(
    sneaker: SneakersResponse,
    onFavoriteClick: (Int, Boolean) -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageRes = getDrawableId(sneaker.imageUrl)

    Column(
        modifier = modifier
            .width(160.dp)
            .height(240.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {
                    onFavoriteClick(sneaker.id, !sneaker.isFavorite)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(
                        if (sneaker.isFavorite) R.drawable.new_heart else R.drawable.new_empty_heart
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(0.3f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = sneaker.category.uppercase(),
                color = MatuleTheme.colors.accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = sneaker.name,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, lineHeight = 16.sp),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp),
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₽${"%.2f".format(sneaker.price)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(
                    onClick = onAddToCart,
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.new_add),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}