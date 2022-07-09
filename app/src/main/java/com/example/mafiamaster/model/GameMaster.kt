package com.example.mafiamaster.model

import com.example.mafiamaster.activities.GameActivity

class GameMaster(
    private val playersMap: HashMap<Int, Player>,
    private val activity: GameActivity
    private var isGameOver: Boolean = false
) {
    init {
        firstCycle()
    }

    private fun firstCycle() {
        startNightOfGettingAcquaintances()
        startTalk()
        startSpeeches()
        startVoting()
        startLastWords()
    }

    private fun generalCycle() {
        startNight()
        startLastWords()
        startTalk()
        startSpeeches()
        startVoting()
        startLastWords()
        if (isGameOver) {
            finishTheGame()
        } else {
            generalCycle()
        }
    }

    private fun startNightOfGettingAcquaintances() {
        activity.hideAllActions()
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