import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import kotlinx.coroutines.launch

@Composable
fun SlidesScrn(
    onNavigateToSignInScrn: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        com.example.myapplication.data.local.OnboardingPage(
            welcome = stringResource(R.string.welcome),
            title = "",
            description = "",
            image = R.drawable.buket,
            image2 = R.drawable.curlicues
        ),
        com.example.myapplication.data.local.OnboardingPage(
            welcome = "",
            title = stringResource(R.string.welcome_page2_title),
            description = stringResource(R.string.welcome_page2_desc),
            image = R.drawable.buket2,
            image2 = R.drawable.curlicues
        ),
        com.example.myapplication.data.local.OnboardingPage(
            welcome = "",
            title = stringResource(R.string.welcome_page3_title),
            description = stringResource(R.string.welcome_page3_desc),
            image = R.drawable.buket3,
            image2 = R.drawable.curlicues
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFba045f), Color(0xFF660033))))
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = pages[page].welcome,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(bottom = 40.dp)
                            .scale(if (page == 1) 1.4f else 1.7f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = pages[page].image2),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(if (page == 2) 600.dp else 800.dp)
                                .let {
                                    if (page == 2) it.offset(y = (-42).dp) else it
                                }
                        )

                        Image(
                            painter = painterResource(id = pages[page].image),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(400.dp)
                                .let {
                                    if (page == 0) it.offset(y = 42.dp) else it
                                }
                        )
                    }

                    Text(
                        text = pages[page].title,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Text(
                        text = pages[page].description,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = if (page > 0) 18.sp else 20.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 25.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pages.size) { index ->
                val isSelected = pagerState.currentPage == index

                val width by animateDpAsState(targetValue = if (isSelected) 40.dp else 16.dp, label = "indicator_width")
                val color by animateColorAsState(targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.4f), label = "indicator_color")

                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(width)
                        .background(color = color, shape = RoundedCornerShape(50))
                )

                if (index != pages.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < pages.lastIndex) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        onNavigateToSignInScrn()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = when (pagerState.currentPage) {
                0 -> "Начать"
                1 -> "Далее"
                2 -> "Далее"
                else -> ""
            }, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}