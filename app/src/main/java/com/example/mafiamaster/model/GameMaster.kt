package com.example.mafiamaster.model

import android.widget.Toast
import com.example.mafiamaster.activities.GameActivity
import com.example.mafiamaster.utils.GameFlowConstants

class GameMaster(
    private val playersMap: HashMap<Int, Player>,
    private val activity: GameActivity,
) {

    private var isGameOver: Boolean = false
    private var currentPart = GameFlowConstants.NIGHT_OF_GETTING_ACQUAINTANCES
    private var isSomebodyKilled = false
    private var playerSpeaking = 1


    init {
        startCurrentPart()
    }

    private fun startCurrentPart() {
        when (currentPart) {
            GameFlowConstants.NIGHT_OF_GETTING_ACQUAINTANCES -> {
                startNightOfGettingAcquaintances()
            }
            GameFlowConstants.NIGHT -> {
                startNight()
            }
            GameFlowConstants.LAST_WORDS_AFTER_NIGHT -> {
                startLastWords()
            }
            GameFlowConstants.TALK -> {
                startTalk()
            }
            GameFlowConstants.SPEECHES -> {
                startSpeeches()
            }
            GameFlowConstants.VOTING -> {
                startVoting()
            }
            GameFlowConstants.LAST_WORDS_AFTER_VOTING -> {
                startLastWords()
            }

        }
    }

    fun goToTheNextPart() {
        if (isGameOver) {
            finishTheGame()
        } else {
            currentPart++
            startCurrentPart()
        }
    }

    fun goToThePartAfterGettingAcquaintances() {
        currentPart = GameFlowConstants.TALK
        startCurrentPart()
    }

    private fun startNightOfGettingAcquaintances() {
        activity.hideAllActions()
        activity.showNightOfGettingAcquaintancesAction()
    }

    private fun startTalk() {
        activity.hideAllActions()
        activity.showTalkAction()
    }

    private fun startSpeeches() {
        if (playerSpeaking == getAlivePlayersAmount()) {

        } else {
            activity.showSpeechAction(playerSpeaking)
            playerSpeaking++
        }
    }

    private fun startVoting() {
        activity.hideAllActions()
        activity.showVotingAction()
    }

    private fun startLastWords() {
        if (isSomebodyKilled) {
            activity.hideAllActions()
            activity.showKilledPlayersRoleAction()
        } else {

        }
    }

    private fun startNight() {
        activity.hideAllActions()
        activity.showNightAction()
    }

    private fun finishTheGame() {

    }

    fun timerFinished() {
        when (currentPart) {
            GameFlowConstants.TALK -> {
                goToTheNextPart()
            }
            GameFlowConstants.SPEECHES -> {

            }
        }
    }

    private fun getAlivePlayersAmount(): Int {
        var alivePlayersAmount = 0
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.alive) {
                alivePlayersAmount++
            }
        }
        return alivePlayersAmount
    }

}