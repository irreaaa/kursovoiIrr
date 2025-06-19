import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.data.remote.network.response.SneakersResponse
import com.example.myapplication.ui.screen.Cart.CartItemCard
import com.example.myapplication.ui.screen.Popular.PopularViewModel
import com.example.myapplication.ui.screen.component.AuthButton
import com.example.myapplication.ui.theme.MatuleTheme
import org.koin.androidx.compose.koinViewModel


const val deliveryCost = 60.20f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScrn(
    navController: NavController,
    viewModel: PopularViewModel = koinViewModel()
) {

    val favoritesState by viewModel.favoritesState.collectAsState()
    val cartState by viewModel.cartState.collectAsState()

    val cartCounts = remember { mutableStateMapOf<Int, Int>() }

    val purchaseSum = when (cartState) {
        is NetworkResponseSneakers.Success -> {
            val carts = (cartState as NetworkResponseSneakers.Success<List<SneakersResponse>>).data
            carts.sumOf { item ->
                val count = cartCounts[item.id] ?: 1
                (item.price ?: 0f).toDouble() * count.toDouble()
             }.toFloat()

        }
        else -> 0f
    }



    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCart()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Корзина", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.Black)
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
            )
        },
        bottomBar = {
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                if(maxHeight > 0.dp){
                    val heightOfWhiteRect = maxHeight / 3

                    Surface(color = Color.White, modifier = Modifier
                        .fillMaxWidth()
                        .height(heightOfWhiteRect)
                        .align(Alignment.BottomCenter)){

                        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(top = 28.dp, start = 15.dp, end = 15.dp)) {

                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                                Text("Сумма:", style = MaterialTheme.typography.titleMedium, color = MatuleTheme.colors.subTextDark)
                                Text("₽${purchaseSum}", style = MaterialTheme.typography.titleMedium, color = Color.Black, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                                Text("Доставка:", style = MaterialTheme.typography.titleMedium, color = MatuleTheme.colors.subTextDark)
                                Text("₽$deliveryCost", style = MaterialTheme.typography.titleMedium, color = Color.Black, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(modifier = (Modifier.padding(8.dp)), color = Color(0xFFe0adc7))
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                                Text("Итого:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("₽${(purchaseSum + deliveryCost)}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MatuleTheme.colors.accent)
                            }


                            AuthButton(
                                onClick = {}
                            ) {
                                Text(stringResource(R.string.cart_button))
                            }
                            Spacer(Modifier.padding(bottom = 60.dp))
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        when (cartState) {
            is NetworkResponseSneakers.Success -> {
                val carts = (cartState as NetworkResponseSneakers.Success<List<SneakersResponse>>).data

                if (carts.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "В корзине пока пусто",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFC189A5)
                        )
                    }
                } else {
                    CartContent(
                        modifier = Modifier.padding(paddingValues),
                        cart = carts,
                        onItemClick = { id ->
                        },
                        onFavoriteClick = { id, isFavorite ->
                            viewModel.toggleFavorite(id, isFavorite)
                        },
                        inCartClick = {id, inCart ->
                            viewModel.toggleCart(id, inCart)
                        },
                        navController = navController,
                        cartCounts
                    )
                }
            }
            is NetworkResponseSneakers.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Ошибка загрузки",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            NetworkResponseSneakers.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Загрузка...",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun CartContent(
    modifier: Modifier = Modifier,
    cart: List<SneakersResponse>,
    onItemClick: (Int) -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit,
    inCartClick: (Int, Boolean) -> Unit,
    navController: NavController,
    cartCounts: MutableMap<Int, Int>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 150.dp)
    ) {
        items(
            items = cart,
            key = { it.id }
        ) { sneaker ->
            CartItemCard(
                sneaker = sneaker,
                count = cartCounts[sneaker.id] ?: 1,
                onQuantityChanged = { id, newCount ->
                    cartCounts[id] = newCount
                },
                onDeleteClick = { id ->
                    inCartClick(id, false)
                    cartCounts.remove(id)
                }
            )

        }
    }
}
