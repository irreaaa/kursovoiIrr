package com.example.myapplication.ui.screen.Otp

import android.health.connect.datatypes.units.Length
import android.os.CountDownTimer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.HistoricalChange
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.screen.component.TitleWithSubtitleText
import com.example.myapplication.ui.theme.MatuleTheme
import kotlin.time.Duration.Companion.seconds

@Composable
fun OtpScrn(title: String = "", onNavigationToProfile: () -> Unit) {
    val viewModel: OtpViewModel = viewModel()
    val otpState by viewModel.otpState.observeAsState()

    var otpValue by remember { mutableStateOf("") }
    val hasError = otpValue.length < 6

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp),
        verticalArrangement = Arrangement.Center) {

        Row(
            modifier = Modifier
                .padding(top = 35.dp)
                .fillMaxWidth()
                .height(40.dp)
        ) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        TitleWithSubtitleText(
            title = stringResource(R.string.otp_proverka),
            subTitle = stringResource(R.string.otp_text)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "ОТР Код",
                    style = MatuleTheme.typography.headingBold24.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Left
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OtpTextField(
                    value = otpValue,
                    length = 6,
                    hasError= hasError,
                    onValueChange = {otpValue = it}
                )
            }
        }


        Row(modifier = Modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = {}) {
                Text(
                    text = stringResource(R.string.send_again),
                    style = MatuleTheme.typography.subTitleRegular16.copy(color = MatuleTheme.colors.subTextDark)
                )
            }

            BaseTimerText(text = {
                Text(text = it, style = MatuleTheme.typography.subTitleRegular16.copy(color = MatuleTheme.colors.subTextDark))
            },
                period = 60,
                action = {})



        }
    }
}

@Preview
@Composable
fun testOtpTextField(){
    val text =  remember { mutableStateOf("")}
    OtpTextField(text.value, 6, hasError = false){
        text.value = it
    }
}

@Composable
fun OtpTextField(
    value: String,
    length: Int,
    hasError: Boolean = false,
    modifier: Modifier = Modifier,
    boxWidth: Dp = 50.dp,
    boxHeight: Dp= 100.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit)  {


    BasicTextField(
        value = value,
        onValueChange = {
            if(it.length <= length){
                onValueChange(it)
            }
        },
        modifier = modifier,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        decorationBox = {
            val spaceBoxBetween = 12.dp
            Row(
                modifier = Modifier
                    .size((boxWidth + spaceBoxBetween) * length, height = boxHeight),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                val border = BorderStroke(
                    width = 1.dp,
                    color = if(hasError)  Color.Red  else Color.Blue
                )
                repeat(length){
                    Box(
                        modifier = Modifier
                            .width(boxWidth)
                            .height(boxHeight)
                            .clip(shape = RoundedCornerShape(size = 12.dp))
                            .background(Color.LightGray)
                            .border(border, shape = RoundedCornerShape(size = 12.dp)),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = value.getOrNull(it)?.toString() ?: "")
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun previewAuthTopBar(){
    AuthorizeTopBar {  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizeTopBar(title: String = "", onNavigationToProfile: () -> Unit){
    TopAppBar(title =  {
        Text(text = title)
    }, navigationIcon = {
        IconButton(
            onNavigationToProfile,
        ) {
            Icon(painterResource(R.drawable.ic_launcher_foreground), contentDescription = null)
        }
    }
    )
}


@Preview
@Composable
fun PreviewBaseTimerText(){
    BaseTimerText(text = {
        Text(text = it)
    }, period = 61){

    }
}

@Composable
fun BaseTimerText(text: @Composable (text:String) -> Unit,
                  period: Long,
                  action: () -> Unit){
    val displayText = remember { mutableStateOf("") }
    val counter = object : CountDownTimer(period * 1000, 1*1000 ){
        override fun onTick(millisUntilFinished: Long) {
            (millisUntilFinished/1000).seconds.toComponents { minutes, seconds, nanoseconds ->
                displayText.value = "$minutes:$seconds"
            }
        }
        override fun onFinish() {
            action()
        }
    }
    LaunchedEffect(Unit) {
        counter.start()
    }
    text(displayText.value)
}