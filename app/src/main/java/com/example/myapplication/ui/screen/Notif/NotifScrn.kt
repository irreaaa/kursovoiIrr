package com.example.myapplication.ui.screen.Notif

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.screen.component.BottomProfile

val purchaseSum = 100.50f
const val deliveryCost = 60.20f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotifScrn(navController: NavController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Уведомления",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_arrow),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.new_notif),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            )
        },
        bottomBar = { BottomProfile(navController) }
    ) { innerPadding ->
        NotifContent(innerPadding)
    }
}

@Composable
fun NotifContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Уведомлений пока нет",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFFC189A5)
        )
    }
}