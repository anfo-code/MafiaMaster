package com.example.mafiamaster.activities

import android.os.Bundle
import android.view.View
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.ActivityGameBinding
import com.example.mafiamaster.model.GameMaster
import com.example.mafiamaster.model.Player
import com.example.mafiamaster.utils.BaseForActivities
import com.example.mafiamaster.utils.Constants
import com.example.mafiamaster.utils.TimerHandler

class GameActivity : BaseForActivities(), View.OnClickListener {

    private lateinit var binding: ActivityGameBinding
    private var playersMap: HashMap<Int, Player> = HashMap()
    private lateinit var gameMaster: GameMaster
    private lateinit var timerHandler: TimerHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getPlayersMapFromIntent()
        gameMaster = GameMaster(playersMap,this)
        timerHandler = TimerHandler(binding, gameMaster, this)

        binding.buttonFinishTheNightOfGettingAcquaintances.setOnClickListener(this)
        binding.constraintLayoutPauseStart.setOnClickListener(this)
        binding.constraintLayoutSkip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonFinishTheNightOfGettingAcquaintances -> {
                gameMaster.goToThePartAfterGettingAcquaintances()
            }
            R.id.constraintLayoutPauseStart -> {
                if (timerHandler.isTimerPaused()) {
                    resumeTimer()
                } else {
                    pauseTimer()
                }
            }
            R.id.constraintLayoutSkip -> {
                timerHandler.skipTimer()
            }
        }
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }

    private fun getPlayersMapFromIntent() {
        playersMap =
            intent.getSerializableExtra(Constants.ROLES_MAP_EXTRA_KEY) as HashMap<Int, Player>
    }

    private fun showTimer(time: Int) {
        binding.timerLayout.visibility = View.VISIBLE
        timerHandler.setTimer(time)
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

    fun showNightOfGettingAcquaintancesAction() {
        binding.nightOfGettingAcquaintancesLayout.visibility = View.VISIBLE
        setMafiaGetsAcquaintancesView()
        binding.dayNightTextView.text = getString(R.string.night)
    }

    fun showTalkAction() {
        showTimer(Constants.TALK_TIME)
        setDayView()
        binding.secondaryTextView.text = getString(R.string.talk)
    }

    fun showSpeechAction(playerNumber: Int) {
        showTimer(Constants.SPEECH_TIME)
        binding.secondaryTextView.text = getString(R.string.speech_of_player, playerNumber)
    }

    fun hideAllActions() {
        binding.timerLayout.visibility = View.GONE
        binding.votingLayout.visibility = View.GONE
        binding.nightActionLayout.visibility = View.GONE
        binding.killedPlayersLayout.visibility = View.GONE
        binding.foulConstraintLayout.visibility = View.GONE
        binding.nightOfGettingAcquaintancesLayout.visibility = View.GONE
    }

    private fun setMafiaGetsAcquaintancesView() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_mafia)
        binding.secondaryTextView.text = getString(R.string.black_players_get_acquaintances)
    }

    private fun setMafiaView() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_mafia)
        binding.secondaryTextView.text = getString(R.string.mafia_kills)
    }

    private fun setMistressView() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_mistress)
        binding.secondaryTextView.text = getString(R.string.mistress_pays_a_visit)
    }

    private fun setDoctorView() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_doctor)
        binding.secondaryTextView.text = getString(R.string.doctor_heals)
    }

    private fun setManiacView() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_maniac)
        binding.secondaryTextView.text = getString(R.string.maniac_kills)
    }

    private fun setSheriffView() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_sheriff)
        binding.secondaryTextView.text = getString(R.string.sheriff_checks)
    }

    private fun setDayView() {
        binding.dayNightTextView.text = getString(R.string.day)
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_day)
    }

    private fun pauseTimer() {
        timerHandler.pauseTimer()
    }

    private fun resumeTimer() {
        timerHandler.resumeTimer()
    }

    private fun skipTimer() {
        gameMaster.timerFinished()
    }
}