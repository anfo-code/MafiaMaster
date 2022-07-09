package com.example.mafiamaster.model

import com.example.mafiamaster.activities.GameActivity
import com.example.mafiamaster.utils.GameFlowConstants

class GameMaster(
    private val playersMap: HashMap<Int, Player>,
    private val activity: GameActivity,
) {

    private var isGameOver: Boolean = false
    private var currentCycle = GameFlowConstants.NIGHT_OF_GETTING_ACQUAINTANCES

    init {
        startCurrentPart()
    }

    private fun startCurrentPart() {
        when (currentCycle) {
            GameFlowConstants.NIGHT_OF_GETTING_ACQUAINTANCES -> {
                startNightOfGettingAcquaintances()
            }
            GameFlowConstants.NIGHT -> {
                startNightOfGettingAcquaintances()
            }
            GameFlowConstants.LAST_WORDS_AFTER_NIGHT -> {
                startNightOfGettingAcquaintances()
            }
            GameFlowConstants.TALK -> {
                startNightOfGettingAcquaintances()
            }
            GameFlowConstants.SPEECHES -> {
                startNightOfGettingAcquaintances()
            }
            GameFlowConstants.VOTING -> {
                startNightOfGettingAcquaintances()
            }
            GameFlowConstants.LAST_WORDS_AFTER_VOTING -> {
                startNightOfGettingAcquaintances()
            }

        }
    }

    fun goToTheNextPart() {
        if (isGameOver) {
            finishTheGame()
        } else {
            currentCycle++
            startCurrentPart()
        }
    }

    fun goToThePartAfterGettingAcquaintances() {
        currentCycle = GameFlowConstants.TALK
        startCurrentPart()
    }

    private fun startNightOfGettingAcquaintances() {
        activity.hideAllActions()
        activity.showNightOfGettingAcquaintancesAction()
    }

    private fun startTalk() {
        activity.hideAllActions()
        activity.showTimer()
    }

    private fun startSpeeches() {

    }

    private fun startVoting() {
        activity.hideAllActions()
        activity.showVotingAction()
    }

    private fun startLastWords() {
        activity.hideAllActions()
        activity.showTimer()
        activity.showKilledPlayersRoleAction()
    }

    private fun startNight() {
        activity.hideAllActions()
        activity.showNightAction()
    }

    private fun finishTheGame() {

    }

}