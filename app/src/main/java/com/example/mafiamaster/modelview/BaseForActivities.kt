package com.example.mafiamaster.modelview

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mafiamaster.R
import com.example.mafiamaster.constants.Constants

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

    fun getRoleNameFromCode(roleCode: Int): String {
        return when(roleCode) {
            Constants.CIVILIAN -> getString(R.string.civilian)
            Constants.MAFIA -> getString(R.string.mafia)
            Constants.MISTRESS -> getString(R.string.mistress)
            Constants.DON -> getString(R.string.don)
            Constants.DOCTOR -> getString(R.string.doctor)
            Constants.MANIAC -> getString(R.string.maniac)
            Constants.SHERIFF -> getString(R.string.sheriff)
            else -> ""
        }
    }
}