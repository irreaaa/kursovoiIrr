package com.example.myapplication

import CartScrn
import SlidesScrn
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.domain.usecase.AuthUseCase
import com.example.myapplication.data.local.DataStoreOnBoarding
import com.example.myapplication.data.remote.network.response.NetworkResponseSneakers
import com.example.myapplication.ui.screen.Cart.OrderConfirmationScreen
import com.example.myapplication.ui.screen.Favourite.FavoriteScrn
import com.example.myapplication.ui.screen.Home.HomeScreen
import com.example.myapplication.ui.screen.Listing.ListingScrn
import com.example.myapplication.ui.screen.Notif.NotifScrn
import com.example.myapplication.ui.screen.Otp.OtpScrn
import com.example.myapplication.ui.screen.Popular.PopularScrn
import com.example.myapplication.ui.screen.Popular.PopularViewModel
import com.example.myapplication.ui.screen.Profile.ProfileMenuScreen
import com.example.myapplication.ui.screen.Profile.ProfileViewModel
import com.example.myapplication.ui.screen.RecoverPassword.RecoverPasswordScrn
import com.example.myapplication.ui.screen.SignIn.SignInScrn
import com.example.myapplication.ui.screen.SignUp.SignUpScrn
import com.example.myapplication.ui.screen.Welcome.SplashScreen
import com.example.myapplication.ui.screen.Welcome.SplashViewModel
import com.example.myapplication.ui.theme.MatuleTheme
import kotlinx.serialization.Serializable
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val dataStore = DataStoreOnBoarding(LocalContext.current)
            val authUseCase: AuthUseCase = get()
            MatuleTheme {
                NavHost(navController, startDestination = SplashScreen){

                    composable<SplashScreen> {
                        val splashViewModel: SplashViewModel = koinViewModel()

                        SplashScreen(
                            viewModel = splashViewModel,
                            onNavigateToSignIn = {
                                navController.navigate(SignIn) {
                                    popUpTo(SplashScreen) { inclusive = true }
                                }
                            },
                            onNavigateToSlides = {
                                navController.navigate(Slides) {
                                    popUpTo(SplashScreen) { inclusive = true }
                                }
                            },
                            onNavigateToHome = {
                                navController.navigate(Home) {
                                    popUpTo(SplashScreen) { inclusive = true }
                                }
                            }
                        )
                    }

//
                    composable<Slides> {
                        SlidesScrn(
                            onNavigateToSignInScrn = {
                                navController.navigate(route = SignIn){
                                    popUpTo(route = Slides) {inclusive = true}
                                }
                            }
                        )
                    }
//
                    composable<SignIn> {
                        SignInScrn(
                            navController = navController,
                            onSignInSuccess = {
                                //navController.navigate(route = Otp)
                                navController.navigate(route = Home)
                            }
                        )
                    }
//
                    composable<RecoverPassword> {
                        RecoverPasswordScrn(navController)
                    }
//
                    composable<Registration> {
                        SignUpScrn(
                            onNavigationToHome = {
                                navController.navigate(route = Home)
                            },
                            navController = navController
                        )
                    }

                    composable<Otp> {
                        OtpScrn {
                            navController.navigate(route = Home)
                        }
                    }

                    composable<Home> {
                        HomeScreen(
                            navController)
                    }

                    composable<Popular> {
                        PopularScrn(navController)
                    }

                    composable<Favourite> {
                        FavoriteScrn(navController)
                    }

                    composable<Listing> {
                        ListingScrn(navController)
                    }

                    composable<Cart> {
                        CartScrn(navController)
                    }

                    composable<Profile> {
                        val viewModel: ProfileViewModel = koinViewModel()
                        ProfileMenuScreen(
                            navController = navController,
                            viewModel = viewModel,
                            authUseCase = authUseCase
                        )
                    }

                    composable<Notif> {
                        NotifScrn(navController)
                    }

                    composable<OrderConfirmation> {
                        val viewModel: PopularViewModel = koinViewModel()
                        LaunchedEffect(Unit) {
                            viewModel.fetchCart()
                        }
                        val cartState = viewModel.cartState.collectAsState().value
                        val cartCounts by viewModel._cartCounts.collectAsState()

                        when (cartState) {
                            is NetworkResponseSneakers.Success -> {
                                OrderConfirmationScreen(
                                    navController = navController,
                                    cartItems = cartState.data,
                                    cartCounts = cartCounts,
                                    deliveryCost = 60.20f
                                )
                            }

                            is NetworkResponseSneakers.Error -> {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("Ошибка загрузки данных", color = Color.Red)
                                }
                            }

                            NetworkResponseSneakers.Loading -> {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object SplashScreen
@Serializable
object Registration
@Serializable
object Profile
@Serializable
object RecoverPassword
@Serializable
object SignIn
@Serializable
object Otp
@Serializable
object Slides
@Serializable
object Home
@Serializable
object Popular
@Serializable
object Favourite
@Serializable
object Listing
@Serializable
object Cart
@Serializable
object Notif
@Serializable
object OrderConfirmation