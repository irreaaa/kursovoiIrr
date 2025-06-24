package com.example.myapplication.ui.screen.Favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.data.remote.network.response.SneakersResponse
import com.example.myapplication.ui.screen.Home.ProductItem
import com.example.myapplication.ui.screen.Popular.PopularViewModel
import com.example.myapplication.ui.screen.component.BottomProfile
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScrn(
    navController: NavController,
    viewModel: PopularViewModel = koinViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var favoritesWithCart by remember { mutableStateOf<List<SneakersResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        favoritesWithCart = viewModel.getFavoritesMergedWithCart()
        isLoading = false
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Избранное", fontSize = 20.sp, fontWeight = FontWeight.Medium)
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
                }
            )
        },
        bottomBar = { BottomProfile(navController) }
    ) { paddingValues ->

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else if (favoritesWithCart.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("В избранных пока пусто", fontSize = 18.sp, color = Color(0xFFC189A5))
            }
        } else {
            FavoriteContent(
                modifier = Modifier.padding(paddingValues),
                favorites = favoritesWithCart,
                onItemClick = { },
                onFavoriteClick = { id, isFavorite ->
                    viewModel.toggleFavorite(id, isFavorite)
                    coroutineScope.launch {
                        favoritesWithCart = viewModel.getFavoritesMergedWithCart()
                    }
                },
                inCartClick = { id, inCart ->
                    viewModel.toggleCart(id, inCart)
                    coroutineScope.launch {
                        favoritesWithCart = viewModel.getFavoritesMergedWithCart()
                    }
                },
                navController = navController
            )
        }
    }
}



@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    favorites: List<SneakersResponse>,
    onItemClick: (Int) -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit,
    inCartClick: (Int, Boolean) -> Unit,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = favorites,
            key = { it.id }
        ) { sneaker ->
            ProductItem(
                sneaker = sneaker,
                onFavoriteClick = { _, isFavorite ->
                    onFavoriteClick(sneaker.id, isFavorite)
                },
                onAddToCart = inCartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.65f)
            )
        }
    }
}