package com.example.myapplication.ui.screen.SignUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.SignIn
import com.example.myapplication.ui.screen.component.AuthButton
import com.example.myapplication.ui.screen.component.AuthTextField
import com.example.myapplication.ui.screen.component.TitleWithSubtitleText
import com.example.myapplication.ui.theme.MatuleTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScrn(onNavigationToHome: () -> Unit, navController: NavController) {
    val signUpViewModel: SignUpViewModel = koinViewModel()
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
                        contentDescription = null
                    )
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
                    text = stringResource(R.string.sign_in),
                    modifier = Modifier.clickable {
                        navController.navigate(route = SignIn)
                    },
                    style = MatuleTheme.typography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
    {
            paddingValues ->
        SignUpContent(paddingValues, signUpViewModel)

        val registrationScreenState = signUpViewModel.signUpState
        LaunchedEffect(registrationScreenState.value.isSignedIn) {
            if (registrationScreenState.value.isSignedIn) {
                onNavigationToHome()
            }
        }

        LaunchedEffect(registrationScreenState.value.errorMessage) {
            registrationScreenState.value.errorMessage?.let {
                snackBarHostState.showSnackbar(it)
            }
        }
    }
}

@Composable
fun SignUpContent(paddingValues: PaddingValues, signUpViewModel: SignUpViewModel) {
    val signUpState = signUpViewModel.signUpState
    val isChecked = remember { mutableStateOf(false) }
    val passwordVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleWithSubtitleText(
            title = stringResource(R.string.registration),
            subTitle = stringResource(R.string.sign_in_subtitle)
        )
        Spacer(modifier = Modifier.height(35.dp))

        AuthTextField(
            value = signUpState.value.name,
            onChangeValue = { signUpViewModel.setName(it) },
            isError = false,
            placeholder = { Text(text = stringResource(R.string.enter_name)) },
            supportingText = { Text(text = stringResource(R.string.incorrect_name)) },
            label = { Text(text = stringResource(R.string.name)) }
        )

        AuthTextField(
            value = signUpState.value.email,
            onChangeValue = { signUpViewModel.setEmail(it) },
            isError = signUpViewModel.emailHasError.value,
            placeholder = { Text(text = stringResource(R.string.template_email)) },
            supportingText = { Text(text = stringResource(R.string.enter_email)) },
            label = { Text(text = stringResource(R.string.email)) }
        )

        AuthTextField(
            value = signUpState.value.password,
            onChangeValue = { signUpViewModel.setPassword(it) },
            isError = false,
            placeholder = { Text(text = stringResource(R.string.password_template)) },
            supportingText = { Text(text = stringResource(R.string.incorrect_password)) },
            label = { Text(text = stringResource(R.string.password)) },
            isPasswordField = true,
            passwordVisible = passwordVisible.value,
            onVisibilityChange = { passwordVisible.value = !passwordVisible.value }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = it }
            )
            Text(
                text = stringResource(R.string.privacy_policy),
                modifier = Modifier.padding(start = 20.dp),
                style = MatuleTheme.typography.bodyRegular12.copy(
                    color = MatuleTheme.colors.subTextDark,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
        val coroutine = rememberCoroutineScope{Dispatchers.IO}
        AuthButton(onClick = {
//            val user = User(userName = signUpState.value.name, email = signUpState.value.email, password = signUpState.value.password)

            signUpViewModel.signUp();
            coroutine.launch {
//                RetrofitClient.retrofit.registration(user)
            }
        }) {
            Text(stringResource(R.string.sign_up))
            if(signUpState.value.isLoading)CircularProgressIndicator(color = Color.White)
        }
    }
}