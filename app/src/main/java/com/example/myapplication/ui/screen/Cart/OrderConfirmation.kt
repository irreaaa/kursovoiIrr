package com.example.myapplication.ui.screen.Cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.OrderConfirmation
import com.example.myapplication.R
import com.example.myapplication.data.remote.network.response.SneakersResponse
import com.example.myapplication.ui.screen.component.AuthButton
import com.example.myapplication.ui.theme.MatuleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(
    navController: NavController,
    cartItems: List<SneakersResponse>,
    cartCounts: Map<Int, Int>,
    deliveryCost: Float
) {
    val totalSum = cartItems.sumOf { item ->
        val count = cartCounts[item.id] ?: 1
        (item.price ?: 0f).toDouble() * count.toDouble()
    }.toFloat()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Оформление заказа", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Ваш заказ:", style = MaterialTheme.typography.titleMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    val count = cartCounts[item.id] ?: 1
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${item.name} x$count", fontSize = 16.sp)
                        Text(text = "₽${(item.price ?: 0f).toDouble() * count}", fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Доставка:", fontSize = 16.sp)
                Text("₽$deliveryCost", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Итого:", fontSize = 18.sp, color = Color.Black)
                Text("₽${totalSum + deliveryCost}", fontSize = 18.sp, color = MatuleTheme.colors.accent)
            }
            Spacer(modifier = Modifier.height(16.dp))
            AuthButton(
                onClick = {}
            ) {
                Text(stringResource(R.string.cart_confirm_button))
            }
        }
    }
}