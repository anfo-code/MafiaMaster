package com.example.mafiamaster.model

import com.example.mafiamaster.activities.GameActivity
import com.example.mafiamaster.utils.GameFlowConstants

class GameMaster(
    private val playersMap: HashMap<Int, Player>,
    private val activity: GameActivity,
) {

    private var isGameOver: Boolean = false
    private var currentPart = GameFlowConstants.NIGHT_OF_GETTING_ACQUAINTANCES
    private var isSomebodyKilled = false
    private var playerSpeakingCount = 1
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
        activity.startCurrentPlayerSpeech()
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
                if (playerSpeakingCount == getAlivePlayersAmount()) {
                    goToTheNextPart()
                } else {
                    nextPlayerSpeech()
                }
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

    private fun nextPlayerSpeech() {
        playerSpeakingCount++
        playerSpeaking = getPlayerByCount()
        activity.startCurrentPlayerSpeech()
    }

    //this function gets an alive player by playerSpeaking count
    //if current playerSpeaker is 3, it gets third alive player in the list
    private fun getPlayerByCount(): Int {
        var playerFromCount = 0
        var cycle = 1
        var checkedPlayer = 1
        while (cycle !== playerSpeakingCount) {
            if (playersMap[checkedPlayer]!!.alive) {
                cycle++
                checkedPlayer++
            } else {
                checkedPlayer++
            }
        }
        playerFromCount = checkedPlayer

        return playerFromCount
    }

    fun getCurrentPlayerSpeaking(): Int {
        return playerSpeaking
    }

    fun addVoteToPlayer(player: Int) {
        playersMap[player]!!.votesAmount++
    }

    fun removeVoteFromPlayer(player: Int) {
        playersMap[player]!!.votesAmount--
    }

    fun getCurrentPlayersMap(): HashMap<Int, Player> {
        return playersMap
    }

    fun getAlivePlayersMap(): HashMap<Int, Player> {
        var alivePlayersMap: HashMap<Int, Player> = HashMap()
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.alive) {
                alivePlayersMap[player] = playersMap[player]!!
            }
        }
        return alivePlayersMap
    }

    fun getAlivePlayersNumbersArrayList(): ArrayList<Int> {
        var alivePlayersNumberArrayList: ArrayList<Int> = ArrayList()
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.alive) {
                alivePlayersNumberArrayList.add(player)
            }
        }
        return alivePlayersNumberArrayList
    }
}