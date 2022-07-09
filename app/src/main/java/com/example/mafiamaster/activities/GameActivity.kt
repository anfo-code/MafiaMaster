package com.example.mafiamaster.activities

import android.os.Bundle
import android.view.View
import com.example.mafiamaster.databinding.ActivityGameBinding
import com.example.mafiamaster.utils.BaseForActivities

class GameActivity : BaseForActivities(), View.OnClickListener {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

        }
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }

    fun showTimer() {
        binding.timerLayout.visibility = View.VISIBLE
    }

    fun showVotingAction() {
        binding.votingLayout.visibility = View.VISIBLE
    }

    fun showNightAction() {
        binding.nightActionLayout.visibility = View.VISIBLE
    }

    fun showKilledPlayersRoleAction() {
        binding.killedPlayersLayout.visibility = View.VISIBLE
    }

    fun showThreeFoulAction() {
        binding.foulConstraintLayout.visibility = View.VISIBLE
    }

    fun showNightOdGettingAcquaintancesAction() {
        binding.nightOfGettingAcquaintancesLayout.visibility = View.VISIBLE
    }

    fun hideAllActions() {
        binding.timerLayout.visibility = View.GONE
        binding.votingLayout.visibility = View.GONE
        binding.nightActionLayout.visibility = View.GONE
        binding.killedPlayersLayout.visibility = View.GONE
        binding.foulConstraintLayout.visibility = View.GONE
    }
}