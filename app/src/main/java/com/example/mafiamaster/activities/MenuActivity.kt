package com.example.mafiamaster.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.ActivityMenuBinding
import com.example.mafiamaster.utils.BaseForActivities

class MenuActivity : BaseForActivities(), View.OnClickListener {

    //TODO Finish UI for activities, and make a commit stating, that UI was added

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMenuBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        turnOnDarkMode()

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.buttonStartGame -> {
               startGame()
            }
            R.id.buttonSettings -> {
                startSettingsActivity()
            }
            R.id.buttonRules -> {
                startRulesActivity()
            }
        }
    }

    private fun startGame() {
        startActivity(Intent(this, GameSettingActivity::class.java))
    }

    private fun startSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun startRulesActivity() {
        startActivity(Intent(this, RulesActivity::class.java))
    }
}