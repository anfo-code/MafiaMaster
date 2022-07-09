package com.example.mafiamaster.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.ActivityGameSettingBinding
import com.example.mafiamaster.model.RolesCountHandler
import com.example.mafiamaster.model.RolesGive
import com.example.mafiamaster.utils.BaseForActivities
import com.example.mafiamaster.utils.Constants

class GameSettingActivity : BaseForActivities(), View.OnClickListener {

    private lateinit var binding: ActivityGameSettingBinding
    private lateinit var rolesCountHandler: RolesCountHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGameSettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initiateRolesCountHandler()

        setToolbar()

        showCurrentRolesCount()

        binding.ivAddPlayers.setOnClickListener(this)
        binding.ivAddMistress.setOnClickListener(this)
        binding.ivAddDoctors.setOnClickListener(this)
        binding.ivAddManiacs.setOnClickListener(this)
        binding.ivAddSheriffs.setOnClickListener(this)
        binding.ivRemovePlayers.setOnClickListener(this)
        binding.ivRemoveMistress.setOnClickListener(this)
        binding.ivRemoveDoctors.setOnClickListener(this)
        binding.ivRemoveManiacs.setOnClickListener(this)
        binding.ivRemoveSheriffs.setOnClickListener(this)
        binding.buttonStartGame.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivAddPlayers -> {
                rolesCountHandler.addPlayer()
            }
            R.id.ivAddMistress -> {
                rolesCountHandler.addMistress()
            }
            R.id.ivAddDoctors -> {
                rolesCountHandler.addDoctor()
            }
            R.id.ivAddManiacs -> {
                rolesCountHandler.addManiac()
            }
            R.id.ivAddSheriffs -> {
                rolesCountHandler.addSheriff()
            }
            R.id.ivRemovePlayers -> {
                rolesCountHandler.removePlayer()
            }
            R.id.ivRemoveMistress -> {
                rolesCountHandler.removeMistress()
            }
            R.id.ivRemoveDoctors -> {
                rolesCountHandler.removeDoctor()
            }
            R.id.ivRemoveManiacs -> {
                rolesCountHandler.removeManiac()
            }
            R.id.ivRemoveSheriffs -> {
                rolesCountHandler.removeSheriff()
            }
            R.id.buttonStartGame -> {
                startTheGame()
            }
        }
        showCurrentRolesCount()
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarMain.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initiateRolesCountHandler() {
        rolesCountHandler = RolesCountHandler()
    }

    private fun showCurrentRolesCount() {
        binding.tvPlayersCount.text = rolesCountHandler.getPlayersCount().toString()
        binding.tvMafiaCount.text = rolesCountHandler.getMafiaCount().toString()
        binding.tvDonCount.text = rolesCountHandler.getDonCount().toString()
        binding.tvMistressesCount.text = rolesCountHandler.getMistressCount().toString()
        binding.tvDoctorsCount.text = rolesCountHandler.getDoctorCount().toString()
        binding.tvManiacsCount.text = rolesCountHandler.getManiacCount().toString()
        binding.tvSheriffsCount.text = rolesCountHandler.getSheriffCount().toString()
    }

    private fun startTheGame() {
        val rolesGive = RolesGive(rolesCountHandler)
        val intent = Intent(this, RolesActivity::class.java)
        intent.putExtra(Constants.ROLES_MAP_EXTRA_KEY, rolesGive.getPlayersMap())
        startActivity(intent)
    }
}