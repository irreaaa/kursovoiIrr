import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.OrderConfirmation
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
    val cartState by viewModel.cartState.collectAsState()
    val cartCounts by viewModel.cartCounts.collectAsState()

    val purchaseSum = when (cartState) {
        is NetworkResponseSneakers.Success -> {
            val carts = (cartState as NetworkResponseSneakers.Success<List<SneakersResponse>>).data
            carts.sumOf { item ->
                val count = cartCounts[item.id] ?: 1
                (item.price ?: 0f).toDouble() * count
            }.toFloat()
        }
        else -> 0f
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCart()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Корзина",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
        bottomBar = {
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                if (maxHeight > 0.dp) {
                    val heightOfWhiteRect = maxHeight / 3

                    Surface(
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightOfWhiteRect)
                            .align(Alignment.BottomCenter)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.padding(top = 28.dp, start = 15.dp, end = 15.dp)
                        ) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Сумма:", style = MaterialTheme.typography.titleMedium, color = MatuleTheme.colors.subTextDark)
                                Text("₽$purchaseSum", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Доставка:", style = MaterialTheme.typography.titleMedium, color = MatuleTheme.colors.subTextDark)
                                Text("₽$deliveryCost", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(Modifier.padding(8.dp), color = Color(0xFFe0adc7))
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Итого:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("₽${purchaseSum + deliveryCost}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MatuleTheme.colors.accent)
                            }

                            var showDialog by remember { mutableStateOf(false) }

                            AuthButton(onClick = {
                                val carts = (cartState as? NetworkResponseSneakers.Success)?.data ?: emptyList()
                                if (carts.isEmpty()) {
                                    showDialog = true
                                } else {
                                    navController.navigate(route = OrderConfirmation)
                                }
                            }) {
                                Text(stringResource(R.string.cart_button))
                            }

                            if (showDialog) {
                                AlertDialog(
                                    onDismissRequest = { showDialog = false },
                                    title = {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(50.dp)
                                                    .clip(CircleShape)
                                                    .background(color = Color(0xFFFFFFFF)),
                                                contentAlignment = Alignment.Center

                                            ){
                                                Image(
                                                    painter = painterResource(id = R.drawable.flowers),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(65.dp),

                                                    )
                                            }

                                            Text(
                                                text = "В корзине ничего нет",
                                                modifier = Modifier.padding(top = 18.dp),
                                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                                            )

                                            Text(
                                                text = "Нельзя оформить пустой заказ",
                                                modifier = Modifier.fillMaxWidth().wrapContentWidth(),
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    },confirmButton = {
                                        TextButton(onClick = { showDialog = false }) {
                                            Text("OK")
                                        }
                                    },
                                )
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
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
                        onItemClick = {},
                        onFavoriteClick = { id, isFavorite -> viewModel.toggleFavorite(id, isFavorite) },
                        inCartClick = { id, inCart -> viewModel.toggleCart(id, inCart) },
                        onQuantityChanged = { id, newCount -> viewModel.updateItemCount(id, newCount) },
                        cartCounts = cartCounts
                    )
                }
            }
            is NetworkResponseSneakers.Error -> {
                Box(Modifier.fillMaxSize()) {
                    Text("Ошибка загрузки", modifier = Modifier.align(Alignment.Center))
                }
            }
            NetworkResponseSneakers.Loading -> {
                Box(Modifier.fillMaxSize()) {
                    Text("Загрузка...", modifier = Modifier.align(Alignment.Center))
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
    onQuantityChanged: (Int, Int) -> Unit,
    cartCounts: Map<Int, Int>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 150.dp)
    ) {
        items(items = cart, key = { it.id }) { sneaker ->
            CartItemCard(
                sneaker = sneaker,
                count = cartCounts[sneaker.id] ?: 1,
                onQuantityChanged = onQuantityChanged,
                onDeleteClick = { id ->
                    inCartClick(id, false)
                    onQuantityChanged(id, 0)
                }
            )
        }
    }
}