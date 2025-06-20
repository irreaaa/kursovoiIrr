package com.example.myapplication.ui.screen.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.Cart
import com.example.myapplication.Favourite
import com.example.myapplication.Home
import com.example.myapplication.Notif
import com.example.myapplication.Profile
import com.example.myapplication.R
import com.example.myapplication.SignIn
import com.example.myapplication.domain.usecase.AuthUseCase
import kotlinx.coroutines.launch

@Composable
fun ProfileMenuScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    authUseCase: AuthUseCase
) {
    val context = LocalContext.current
    val userName by viewModel.userName.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(R.drawable.flowers),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFFf5d0e2))
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = userName,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF660033)
        )

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(modifier = (Modifier.padding(8.dp)), color = Color(0xFFe0adc7))
        Spacer(modifier = Modifier.height(24.dp))

        MenuItem(text = "Домой", icon = R.drawable.new_home) {
            navController.navigate(Home)
        }

        MenuItem(text = "Корзина", icon = R.drawable.new_basket1) {
            navController.navigate(Cart)
        }

        MenuItem(text = "Избранное", icon = R.drawable.new_empty_heart) {
            navController.navigate(Favourite)
        }

        MenuItem(text = "Уведомления", icon = R.drawable.new_notif) {
            navController.navigate(Notif)
        }

        MenuItem(text = "Настройки", icon = R.drawable.settings) {
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(modifier = (Modifier.padding(8.dp)), color = Color(0xFFe0adc7))
        Spacer(modifier = Modifier.height(24.dp))

        val scope = rememberCoroutineScope()

        MenuItem(text = "Выйти", icon = R.drawable.log_out) {
            scope.launch {
                authUseCase.logout(context)
                navController.navigate(SignIn) {
                    popUpTo(Home) { inclusive = true }
                    popUpTo(Profile) { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun MenuItem(text: String, icon: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(39.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 20.sp)
    }
}
