package com.example.mafiamaster.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

open class BaseForActivities: AppCompatActivity() {
    fun turnOffDarkMode() {
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPreferencesEditor: SharedPreferences.Editor = appSettingPrefs.edit()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        sharedPreferencesEditor.putBoolean("NightMode", true)
        sharedPreferencesEditor.apply()
    }
}