package com.example.myapplication.ui.screen.SignIn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.RecoverPassword
import com.example.myapplication.Registration
import com.example.myapplication.ui.screen.component.AuthButton
import com.example.myapplication.ui.screen.component.AuthTextField
import com.example.myapplication.ui.screen.component.TitleWithSubtitleText
import com.example.myapplication.ui.theme.MatuleTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScrn(onSignInSuccess: () -> Unit, navController: NavController){
    val signInViewModel: SignInViewModel = koinViewModel()
    val snackBarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 35.dp, bottom = 40.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.flowers),
                        contentDescription = null)
                }
            }
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = stringResource(R.string.sign_up_first),
                    modifier = Modifier.clickable {
                        navController.navigate(route = Registration)
                    },
                    style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { paddingValues ->
        SignInContent(paddingValues, signInViewModel, navController)

        val authorizationScreenState = signInViewModel.signInState
        LaunchedEffect(authorizationScreenState.value.isSignIn) {
            if(authorizationScreenState.value.isSignIn){
                onSignInSuccess()
            }
        }

        LaunchedEffect(authorizationScreenState.value.errorMessage) {
            authorizationScreenState.value.errorMessage?.let {
                snackBarHostState.showSnackbar(it)
            }
        }
    }
}

@Composable
fun SignInContent(paddingValues: PaddingValues, signInViewModel: SignInViewModel, navController: NavController){
    val signInState = signInViewModel.signInState
    val passwordVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleWithSubtitleText(
            title = stringResource(R.string.hello),
            subTitle = stringResource(R.string.sign_in_subtitle)
        )
        Spacer(modifier = Modifier.height(35.dp))

        AuthTextField(
            value = signInState.value.email,
            onChangeValue = {
                signInViewModel.setEmail(it)
            },
            isError = signInViewModel.emailHasError.value,
            placeholder = {
                Text(text = stringResource(R.string.template_email))
            },
            supportingText = {
                Text(text = stringResource(R.string.incorrect_email))
            },
            label = {
                Text(text = stringResource(R.string.email))
            }
        )
        AuthTextField(
            value = signInState.value.password,
            onChangeValue = {
                signInViewModel.setPassword(it)
            },
            isError = false,
            placeholder = {
                Text(text = stringResource(R.string.password_template))
            },
            supportingText = {
                Text(text = stringResource(R.string.incorrect_password))
            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            isPasswordField = true,
            passwordVisible = passwordVisible.value,
            onVisibilityChange = { passwordVisible.value = !passwordVisible.value }
        )

        Text(
            text = stringResource(R.string.miss_pass),
            modifier = Modifier
                .clickable {
                    navController.navigate(route = RecoverPassword)
                }
                .padding(horizontal = 25.dp)
                .fillMaxWidth(),
            style = MatuleTheme.typography.bodyRegular16.copy(
                color = MatuleTheme.colors.subTextDark,
                textAlign = TextAlign.End
            )
        )

        AuthButton(
            onClick = {
                signInViewModel.signIn()
            }
        ) {
            Text(stringResource(R.string.enter))
            if(signInState.value.isLoading) CircularProgressIndicator(color = Color.White)
        }
    }
}