package com.example.mafiamaster.utils

import android.os.CountDownTimer
import com.example.mafiamaster.activities.GameActivity
import com.example.mafiamaster.databinding.ActivityGameBinding
import com.example.mafiamaster.model.GameMaster
import java.util.*

class TimerHandler(
    private val binding: ActivityGameBinding,
    private val gameMaster: GameMaster
) {
    private lateinit var timer : CountDownTimer
    private var currentTimerTime = 0
    private var currentTimerTimeProgress = 0
    private var pausedTime = 0
    private var isTimerPaused = false


    fun setTimer(timeInSeconds: Int) {
        currentTimerTime = timeInSeconds
        currentTimerTimeProgress = 0
        binding.timeLeftTextView.text = getTimeInMinutesMilliSecondsFormat(timeInSeconds)
        timer = object : CountDownTimer(timeInSeconds.toLong() * 1000, 1000) {
            override fun onTick(millisecondsUntilTheEnd: Long) {
                currentTimerTimeProgress++
                binding.timerProgressBar.progress = timeInSeconds - currentTimerTimeProgress
                binding.timeLeftTextView.text = getTimeInMinutesMilliSecondsFormat(
                    timeInSeconds - currentTimerTimeProgress
                )
            }
            override fun onFinish() {
                currentTimerTimeProgress = 0
                currentTimerTime = 0
                gameMaster.timerFinished()
            }
        }.start()
    }

    fun pauseTimer() {
        timer.cancel()
        pausedTime = currentTimerTime - currentTimerTimeProgress
        isTimerPaused = true
    }

    fun resumeTimer() {
        setTimer(currentTimerTime - currentTimerTimeProgress)
        isTimerPaused = false
    }

    fun skipTimer() {
        timer.cancel()
        timer.onFinish()
    }

    fun isTimerPaused(): Boolean {
        return isTimerPaused
    }

    private fun getTimeInMinutesMilliSecondsFormat(time: Int): String {
        val minutes = time / 60
        val seconds = time % 60

        return String.format(Locale.getDefault(), "%2d:%02d", minutes, seconds)
    }
}