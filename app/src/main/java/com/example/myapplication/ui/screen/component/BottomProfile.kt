package com.example.myapplication.ui.screen.component

import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.Favourite
import com.example.myapplication.R
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.Cart
import com.example.myapplication.Home
import com.example.myapplication.Notif
import com.example.myapplication.Profile


@Composable
fun BottomProfile(navController: NavController) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    val isHomeSelected = currentRoute == Home::class.simpleName
    val isFavouriteSelected = currentRoute == Favourite::class.simpleName

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(R.drawable.niz),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                IconButton(
                    onClick = { navController.navigate(Home) },
                    modifier = Modifier.offset(y = 28.dp)
                ) {
                    Image(
                        painter = if (isHomeSelected) {
                            painterResource(R.drawable.new_home)
                        } else {
                            painterResource(R.drawable.new_home)
                        },
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

                IconButton(onClick = {
                    navController.navigate(Favourite)
                }, modifier = Modifier.offset(y = 28.dp)) {
                    Image(
                        painter =
                            if (isFavouriteSelected) {
                                painterResource(R.drawable.new_empty_heart)
                            } else {
                                painterResource(R.drawable.new_empty_heart)
                            },
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                    )
                }
            }

            IconButton(
                onClick = {
                    navController.navigate(Cart)
                },
                modifier = Modifier
                    .size(112.dp)
                    .offset(y = 0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.new_basket1),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                IconButton(onClick = {
                    navController.navigate(Notif)
                }, modifier = Modifier.offset(y = 28.dp)) {
                    Image(
                        painter = painterResource(R.drawable.new_notif),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

                IconButton(onClick = {
                    navController.navigate(Profile)
                }, modifier = Modifier.offset(y = 28.dp)) {
                    Image(
                        painter = painterResource(R.drawable.new_profile),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

            }
        }
    }
}