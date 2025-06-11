package com.example.myapplication.ui.screen.Otp

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Period

class OtpViewModel: ViewModel() {
    private val _otpState = MutableLiveData(OtpState())
    val otpState: LiveData<OtpState> get() = _otpState

    fun startTimer(period: Long){
        val counter = object : CountDownTimer(period*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished/1000
                _otpState.value = _otpState.value!!.copy(buttonIsEnable = false, timerIsVisible = true)
            }

            override fun onFinish() {
                _otpState.value = _otpState.value!!.copy(buttonIsEnable = false, timerIsVisible = true)
            }
        }
        counter.start()
    }
    fun resetTomer() {
        startTimer(60)
    }
}