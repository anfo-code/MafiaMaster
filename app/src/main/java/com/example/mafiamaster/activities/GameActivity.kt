package com.example.mafiamaster.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.ActivityGameBinding
import com.example.mafiamaster.modelview.GameModelView
import com.example.mafiamaster.model.Player
import com.example.mafiamaster.recyclerviewadapters.NightActionItemAdapter
import com.example.mafiamaster.recyclerviewadapters.VotingItemAdapter
import com.example.mafiamaster.modelview.BaseForActivities
import com.example.mafiamaster.constants.Constants
import com.example.mafiamaster.modelview.TimerHandler

class GameActivity : BaseForActivities(), View.OnClickListener {

    private lateinit var binding: ActivityGameBinding
    private var playersMap: HashMap<Int, Player> = HashMap()
    private lateinit var gameModelView: GameModelView
    private lateinit var timerHandler: TimerHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getPlayersMapFromIntent()
        gameModelView = GameModelView(playersMap, this)
        timerHandler = TimerHandler(binding, gameModelView, this)


        binding.buttonFinishTheNightOfGettingAcquaintances.setOnClickListener(this)
        binding.constraintLayoutPauseStart.setOnClickListener(this)
        binding.constraintLayoutSkip.setOnClickListener(this)
        binding.buttonFinishVoting.setOnClickListener(this)
        binding.killedPlayerRoleButton.setOnClickListener(this)
        binding.buttonForSkippingActivePlayer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonFinishTheNightOfGettingAcquaintances -> {
                gameModelView.goToThePartAfterGettingAcquaintances()
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
                gameModelView.executePlayerWithMostVotes()
            }
            R.id.killedPlayerRoleButton -> {
                gameModelView.goToTheNextPart()
            }
            R.id.buttonForSkippingActivePlayer -> {
                gameModelView.goToTheNextPart()
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
        binding.dayNightTextView.text = getString(R.string.night)
    }

    fun showMafiaAction() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_mafia)
        binding.secondaryTextView.setText(R.string.mafia_kills)
        setupNightActionList(Constants.MAFIA)
    }

    fun showMistressAction() {
        if (gameModelView.isPlayerWithRolePlaying(Constants.MISTRESS)) {
            binding.dayOrNightImageView.setImageResource(R.drawable.ic_mistress)
            binding.secondaryTextView.setText(R.string.mistress_pays_a_visit)
            setupNightActionList(Constants.MISTRESS)
        } else {
            gameModelView.goToTheNextPart()
        }
    }

    fun showDoctorAction() {
        if (gameModelView.isPlayerWithRolePlaying(Constants.DOCTOR)) {
            binding.dayOrNightImageView.setImageResource(R.drawable.ic_doctor)
            binding.secondaryTextView.setText(R.string.doctor_heals)
            if (gameModelView.getCurrentPlayersMap()[gameModelView.findPlayerWithRole(Constants.DOCTOR)]!!.isToBeBlocked) {
                showActionOfMistressVisiting()
            } else {
                setupNightActionList(Constants.DOCTOR)
            }
        } else {
            gameModelView.goToTheNextPart()
        }
    }

    fun showManiacAction() {
        if (gameModelView.isPlayerWithRolePlaying(Constants.MANIAC)) {
            binding.dayOrNightImageView.setImageResource(R.drawable.ic_maniac)
            binding.secondaryTextView.setText(R.string.maniac_kills)
            if (gameModelView.getCurrentPlayersMap()[gameModelView.findPlayerWithRole(Constants.MANIAC)]!!.isToBeBlocked) {
                showActionOfMistressVisiting()
            } else {
                setupNightActionList(Constants.MANIAC)
            }
        } else {
            gameModelView.goToTheNextPart()
        }
    }

    fun showSheriffAction() {
        if (gameModelView.isPlayerWithRolePlaying(Constants.SHERIFF)) {
            binding.dayOrNightImageView.setImageResource(R.drawable.ic_sheriff)
            binding.secondaryTextView.setText(R.string.sheriff_checks)
            if (gameModelView.getCurrentPlayersMap()[gameModelView.findPlayerWithRole(Constants.SHERIFF)]!!.isToBeBlocked) {
                showActionOfMistressVisiting()
            } else {
                setupNightActionList(Constants.SHERIFF)
            }
        } else {
            gameModelView.goToTheNextPart()
        }
    }

    fun showKilledPlayersRoleAction(isVoting: Boolean) {
        binding.killedPlayersLayout.visibility = View.VISIBLE
        binding.dayNightTextView.text = getString(R.string.today_victims_are)
        changeSecondaryTextViewTextAccordingToVictimsAmount()
        val killedPlayersList = gameModelView.getKilledPlayersList()

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

    //TODO встановити правильні списки для ночних гравців
    //TODO MAYBE set the fouls action, but only if everything with the las ones goes smoothly

    private fun showActionOfMistressVisiting() {
        hideAllActions()
        binding.secondaryTextView.text = getString(R.string.this_player_is_visited_by_mistress)
        binding.buttonForSkippingActivePlayer.visibility = View.VISIBLE
    }

    fun showThreeFoulAction() {

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
            getString(R.string.speech_of_player, gameModelView.getCurrentPlayerSpeaking())
    }

    fun hideAllActions() {
        binding.timerLayout.visibility = View.GONE
        binding.votingLayout.visibility = View.GONE
        binding.nightActionLayout.visibility = View.GONE
        binding.killedPlayersLayout.visibility = View.GONE
        binding.foulConstraintLayout.visibility = View.GONE
        binding.nightOfGettingAcquaintancesLayout.visibility = View.GONE
        binding.buttonForSkippingActivePlayer.visibility = View.GONE
    }

    private fun setMafiaGetsAcquaintancesView() {
        binding.dayOrNightImageView.setImageResource(R.drawable.ic_mafia)
        binding.secondaryTextView.text = getString(R.string.black_players_get_acquaintances)
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
        val votingItemAdapter = VotingItemAdapter(this, gameModelView)
        binding.votingRecyclerView.adapter = votingItemAdapter
    }

    private fun setupNightActionList(currentRole: Int) {
        binding.buttonForSkippingActivePlayer.visibility = View.GONE
        binding.nightActionLayout.visibility = View.VISIBLE
        binding.nightActionsRecyclerView.layoutManager = LinearLayoutManager(this)
        val nightActionItemAdapter = NightActionItemAdapter(this, gameModelView, currentRole)
        binding.nightActionsRecyclerView.adapter = nightActionItemAdapter
    }

    private fun changeSecondaryTextViewTextAccordingToVictimsAmount() {
        val killedPlayersList = gameModelView.getKilledPlayersList()
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

    fun finishTheGame() {
        when(gameModelView.getWinner()) {
            Constants.MAFIA_WINNER -> {
                Toast.makeText(this, "Mafia has won!", Toast.LENGTH_LONG).show()
                finish()
            }
            Constants.CIVILIANS_WINNER -> {
                Toast.makeText(this, "Civilians have won!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}