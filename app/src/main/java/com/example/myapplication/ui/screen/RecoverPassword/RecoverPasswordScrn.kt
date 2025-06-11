package com.example.myapplication.ui.screen.RecoverPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.SignIn
import com.example.myapplication.ui.screen.SignIn.SignInScrn
import com.example.myapplication.ui.screen.component.AuthButton
import com.example.myapplication.ui.screen.component.AuthTextField
import com.example.myapplication.ui.screen.component.TitleWithSubtitleText

@Composable
fun RecoverPasswordScrn(navController: NavController){
    val recoverPasswordViewModel: RecoverPasswordViewModel = viewModel()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            )

            {
                IconButton(onClick = {navController.navigate(route = SignIn)}) {
                    Icon(painter = painterResource(R.drawable.back_arrow),
                        contentDescription = null)
                }
            }
        },
    )
    {
            paddingValues ->
        RecoverPasswordContent(paddingValues, recoverPasswordViewModel)
    }
}
@Composable
fun RecoverPasswordContent(
    paddingValues: PaddingValues,
    recoverPasswordViewModel: RecoverPasswordViewModel
){
    val recoverPasswordState = recoverPasswordViewModel.recoverPasswordState

    if (recoverPasswordState.value.showDialog) {
        AlertDialog(
            onDismissRequest = { recoverPasswordViewModel.hideDialog() },
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
                            painter = painterResource(id = R.drawable.mail),
                            contentDescription = null,
                            modifier = Modifier.size(65.dp),

                        )
                    }

                    Text(
                        text = "Проверьте Ваш Email",
                        modifier = Modifier.padding(top = 18.dp),
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = stringResource(R.string.recover_message),
                        modifier = Modifier.fillMaxWidth().wrapContentWidth(),
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { recoverPasswordViewModel.hideDialog() }) {
                    Text("Ок")
                }
            }
        )
    }



    Column(
        modifier = Modifier.padding(top = 100.dp)
    )
    {
        TitleWithSubtitleText(
            title = stringResource(R.string.miss_pass),
            subTitle = stringResource(R.string.enter_your_email)
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )
        AuthTextField(
            value = recoverPasswordState.value.email,
            onChangeValue = {
                recoverPasswordViewModel.setEmail(it)
            },
            isError = recoverPasswordViewModel.emailHasError.value,
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
        AuthButton(
            onClick = {recoverPasswordViewModel.showDialog()}
        ) {
            Text(stringResource(R.string.recover))
        }
    }
}