package com.example.mafiamaster.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.ActivityGameBinding
import com.example.mafiamaster.model.GameMaster
import com.example.mafiamaster.model.Player
import com.example.mafiamaster.recyclerviewadapters.NightActionItemAdapter
import com.example.mafiamaster.recyclerviewadapters.VotingItemAdapter
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
        gameMaster = GameMaster(playersMap, this)
        timerHandler = TimerHandler(binding, gameMaster, this)


        binding.buttonFinishTheNightOfGettingAcquaintances.setOnClickListener(this)
        binding.constraintLayoutPauseStart.setOnClickListener(this)
        binding.constraintLayoutSkip.setOnClickListener(this)
        binding.buttonFinishVoting.setOnClickListener(this)
        binding.killedPlayerRoleButton.setOnClickListener(this)
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
            R.id.buttonFinishVoting -> {
                gameMaster.executePlayerWithMostVotes()
            }
            R.id.killedPlayerRoleButton -> {
                gameMaster.goToTheNextPart()
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
        binding.secondaryTextView.text = getString(R.string.voting)
        setupVotingList()
    }

    fun showNightAction() {
        binding.nightActionLayout.visibility = View.VISIBLE
        binding.dayNightTextView.text = getString(R.string.night)
    }

    fun showMafiaAction() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_mafia)
        binding.secondaryTextView.setText(R.string.mafia_kills)
        setupNightActionList(Constants.MAFIA)
    }

    fun showMistressAction() {
        if (gameMaster.isPlayerWithRolePlaying(Constants.MISTRESS)) {
            binding.dayOrNightImageView.setImageResource(R.drawable.ic_mistress)
            binding.secondaryTextView.setText(R.string.mistress_pays_a_visit)
            setupNightActionList(Constants.MISTRESS)
        } else {
            gameMaster.goToTheNextPart()
        }
    }

    fun showDoctorAction() {
        if (gameMaster.isPlayerWithRolePlaying(Constants.DOCTOR)) {
            binding.dayOrNightImageView.setImageResource(R.drawable.ic_doctor)
            binding.secondaryTextView.setText(R.string.doctor_heals)
            setupNightActionList(Constants.DOCTOR)
            if (gameMaster.getCurrentPlayersMap()[gameMaster.findPlayerWithRole(Constants.DOCTOR)]!!.isToBeBlocked) {

            }
        } else {
            gameMaster.goToTheNextPart()
        }
    }

    fun showManiacAction() {
        if (gameMaster.isPlayerWithRolePlaying(Constants.MANIAC)) {
            binding.dayOrNightImageView.setImageResource(R.drawable.ic_maniac)
            binding.secondaryTextView.setText(R.string.maniac_kills)
            setupNightActionList(Constants.MANIAC)
            if (gameMaster.getCurrentPlayersMap()[gameMaster.findPlayerWithRole(Constants.MANIAC)]!!.isToBeBlocked) {

            }
        } else {
            gameMaster.goToTheNextPart()
        }
    }

    fun showSheriffAction() {
        if (gameMaster.isPlayerWithRolePlaying(Constants.SHERIFF)) {
            binding.dayOrNightImageView.setImageResource(R.drawable.ic_sheriff)
            binding.secondaryTextView.setText(R.string.sheriff_checks)
            setupNightActionList(Constants.SHERIFF)
            if (gameMaster.getCurrentPlayersMap()[gameMaster.findPlayerWithRole(Constants.SHERIFF)]!!.isToBeBlocked) {

            }
        } else {
            gameMaster.goToTheNextPart()
        }
    }

    fun showKilledPlayersRoleAction(isVoting: Boolean) {
        binding.killedPlayersLayout.visibility = View.VISIBLE
        binding.dayNightTextView.text = getString(R.string.today_victims_are)
        changeSecondaryTextViewTextAccordingToVictimsAmount()
        val killedPlayersList = gameMaster.getKilledPlayersList()

        if (killedPlayersList.size == 0) {
            binding.killedPlayersRoleTextView.visibility = View.GONE
        } else {
            binding.killedPlayersRoleTextView.visibility = View.VISIBLE
            setKilledPlayersRoleTextView(killedPlayersList)
        }

        if (isVoting) {
            binding.killedPlayerRoleButton.text = getString(R.string.start_the_night)
        } else {
            binding.dayOrNightImageView.setImageDrawable(getDrawable(R.drawable.ic_day))
            binding.killedPlayerRoleButton.text = getString(R.string.start_the_talk)
        }
    }

    //TODO !!! встановити результат ночі
    //TODO !!! встановити путанські чари

    //TODO встановити правильні списки для ночних гравців
    //TODO MAYBE set the fouls action, but only if everything with the las ones goes smoothly

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

    fun startCurrentPlayerSpeech() {
        startSpeechTimer()
        binding.secondaryTextView.text =
            getString(R.string.speech_of_player, gameMaster.getCurrentPlayerSpeaking())
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
        binding.pauseStartImageView.setImageResource(R.drawable.ic_play)
    }

    private fun resumeTimer() {
        timerHandler.resumeTimer()
        binding.pauseStartImageView.setImageResource(R.drawable.ic_pause)
    }

    private fun startSpeechTimer() {
        showTimer(Constants.SPEECH_TIME)
        pauseTimer()
    }

    private fun setupVotingList() {
        binding.votingRecyclerView.layoutManager = LinearLayoutManager(this)
        val votingItemAdapter = VotingItemAdapter(this, gameMaster)
        binding.votingRecyclerView.adapter = votingItemAdapter
    }

    private fun setupNightActionList(currentRole: Int) {
        binding.nightActionsRecyclerView.layoutManager = LinearLayoutManager(this)
        val nightActionItemAdapter = NightActionItemAdapter(this, gameMaster, currentRole)
        binding.nightActionsRecyclerView.adapter = nightActionItemAdapter
    }

    private fun changeSecondaryTextViewTextAccordingToVictimsAmount() {
        val killedPlayersList = gameMaster.getKilledPlayersList()
        when (killedPlayersList.size) {
            0 -> {
                binding.secondaryTextView.text = getString(R.string.everybody_is_alive)
            }
            1 -> {
                binding.secondaryTextView.text = getString(R.string.player, killedPlayersList[0])
            }
            2 -> {
                binding.secondaryTextView.text = getString(
                    R.string.player_and_player,
                    killedPlayersList[0],
                    killedPlayersList[1]
                )
            }
        }
    }

    private fun setKilledPlayersRoleTextView(killedPlayerList: ArrayList<Int>) {
        if (killedPlayerList.size == 1) {
            binding.killedPlayersRoleTextView.text = getRoleNameFromCode(
                playersMap[killedPlayerList[0]]!!.role
            )
        } else {
            binding.killedPlayersRoleTextView.text = getString(
                R.string.role_and_role,
                getRoleNameFromCode(playersMap[killedPlayerList[0]]!!.role),
                getRoleNameFromCode(playersMap[killedPlayerList[1]]!!.role)
            )
        }
    }
}