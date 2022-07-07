package com.example.mafiamaster.utils

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

open class BaseForActivities: AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false


    fun turnOnDarkMode() {
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPreferencesEditor: SharedPreferences.Editor = appSettingPrefs.edit()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        sharedPreferencesEditor.putBoolean("NightMode", true)
        sharedPreferencesEditor.apply()
    }

    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(
                this,
                "Please, click back again to exit.",
                Toast.LENGTH_SHORT
            ).show()

            Handler(Looper.myLooper()!!)
                .postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}