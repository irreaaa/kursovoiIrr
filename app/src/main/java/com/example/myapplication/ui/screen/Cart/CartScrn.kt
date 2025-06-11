import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.myapplication.ui.screen.Popular.PopularViewModel
import com.example.myapplication.ui.screen.common.CommonButton
import com.example.myapplication.ui.screen.component.AuthButton
import com.example.myapplication.ui.theme.MatuleTheme
import org.koin.androidx.compose.koinViewModel

val purchaseSum = 100.50f
const val deliveryCost = 60.20f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScrn(navController: NavController) {

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

                            Divider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.padding(top = 25.dp, bottom = 25.dp))

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
    ) { innerPadding ->
        CartContent(innerPadding)
    }
}

@Composable
fun CartContent(paddingValues: PaddingValues) {

}