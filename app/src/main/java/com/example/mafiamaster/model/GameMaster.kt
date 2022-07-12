package com.example.mafiamaster.model

import android.util.Log
import com.example.mafiamaster.activities.GameActivity
import com.example.mafiamaster.utils.Constants
import com.example.mafiamaster.utils.GameFlowConstants

class GameMaster(
    private val playersMap: HashMap<Int, Player>,
    private val activity: GameActivity,
) {

    private var isGameOver: Boolean = false
    private var currentPart = GameFlowConstants.NIGHT_OF_GETTING_ACQUAINTANCES
    private var isSomebodyKilled = false
    private var isFirstSpeech = true
    private var killedPlayersList: ArrayList<Int> = ArrayList()
    private var playerSpeaking: Int = 1
    private var round = 1
    private var playerSpeakingCount = 1


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
            GameFlowConstants.MAFIA_KILLS -> {
                startMafiaAction()
            }
            GameFlowConstants.MISTRESS_PAYS_A_VISIT -> {
                startMistressAction()
            }
            GameFlowConstants.DOCTOR_HEALS -> {
                startDoctorAction()
            }
            GameFlowConstants.MANIAC_KILLS -> {
                startManiacAction()
            }
            GameFlowConstants.SHERIFF_CHECKS -> {
                startSheriffAction()
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
            if (currentPart == GameFlowConstants.LAST_WORDS_AFTER_VOTING) {
                currentPart = GameFlowConstants.NIGHT
                startCurrentPart()
            } else {
                currentPart++
                startCurrentPart()
            }
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
        killedPlayersList = ArrayList()
    }

    private fun startSpeeches() {
        if (isFirstSpeech) {
            setFirstSpeaker()
        } else {
            nextPlayerSpeech()
        }
        activity.startCurrentPlayerSpeech()
    }

    private fun setFirstSpeaker() {
        for (player in round until playersMap.size) {
            if (playersMap[player]!!.alive) {
                playerSpeaking = player
                break
            }
        }
        isFirstSpeech = false
    }

    private fun startVoting() {
        activity.hideAllActions()
        activity.showVotingAction()
    }

    private fun startLastWords() {
        if (isSomebodyKilled) {
            activity.hideAllActions()
            activity.showKilledPlayersRoleAction(isVoting())
        } else {

        }
    }

    private fun startNight() {
        killedPlayersList = ArrayList()
        activity.hideAllActions()
        activity.showNightAction()
        goToTheNextPart()
    }

    private fun startMafiaAction() {
        activity.showMafiaAction()
    }

    private fun startMistressAction() {
        activity.showMistressAction()
    }

    private fun startDoctorAction() {
        activity.showDoctorAction()
    }

    private fun startManiacAction() {
        activity.showManiacAction()
    }

    private fun startSheriffAction() {
        activity.showSheriffAction()
        applyTheNightResults()
    }

    private fun finishTheGame() {

    }

    fun timerFinished() {
        when (currentPart) {
            GameFlowConstants.TALK -> {
                goToTheNextPart()
            }
            GameFlowConstants.SPEECHES -> {
                if (playerSpeakingCount == getAlivePlayersAmount() + round - 1) {
                    playerSpeakingCount -= getAlivePlayersAmount() - round
                    round++
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
        playerSpeaking = getPlayerByCount()
        activity.startCurrentPlayerSpeech()
    }

    //this function gets an alive player by playerSpeaking count
    //if current playerSpeaker is 3, it gets third alive player in the list
    private fun getPlayerByCount(): Int {
        val playerFromCount: Int

        val alivePlayersList = getAlivePlayersNumbersArrayList()
        var playerInArray = playerSpeakingCount

        if (playerInArray > alivePlayersList.size - 1) {
            playerInArray = playerSpeakingCount - alivePlayersList.size
        }

        Log.i("PLAYER", playerInArray.toString())
        playerFromCount = alivePlayersList[playerInArray]

        playerSpeakingCount++

        return playerFromCount
    }

    fun getCurrentPlayerSpeaking(): Int {
        return playerSpeaking
    }

    fun addVoteToPlayer(player: Int) {
        playersMap[player]!!.votesAmount++
    }

    fun removeVoteFromPlayer(player: Int) {
        if (playersMap[player]!!.votesAmount > 0) {
            playersMap[player]!!.votesAmount--
        }
    }

    fun getCurrentPlayersMap(): HashMap<Int, Player> {
        return playersMap
    }

    fun getAlivePlayersMap(): HashMap<Int, Player> {
        val alivePlayersMap: HashMap<Int, Player> = HashMap()
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.alive) {
                alivePlayersMap[player] = playersMap[player]!!
            }
        }
        return alivePlayersMap
    }

    private fun getAlivePlayersNumbersArrayList(): ArrayList<Int> {
        val alivePlayersNumberArrayList: ArrayList<Int> = ArrayList()
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.alive) {
                alivePlayersNumberArrayList.add(player)
            }
        }
        return alivePlayersNumberArrayList
    }

    fun executePlayerWithMostVotes() {
        val playerToBeExecuted = getPlayerWithMostVotes()
        clearTheVotes()
        killThePlayer(playerToBeExecuted)
        goToTheNextPart()
    }

    private fun getPlayerWithMostVotes(): Int {
        //Start comparing from the first alive player
        var numberOfPlayerWithMostVotes = 1
        for (player in 1 until playersMap.size) {
            Log.i("PLAYER", "$player votes ${playersMap[player]!!.votesAmount}")
            if (playersMap[numberOfPlayerWithMostVotes]!!.votesAmount
                < playersMap[player]!!.votesAmount
            ) {
                numberOfPlayerWithMostVotes = player
            }
        }
        for (player in 1 until playersMap.size - 1) {
            playersMap[player]!!.votesAmount = 0
        }

        return numberOfPlayerWithMostVotes
    }

    private fun clearTheVotes() {
        for (player in 1..playersMap.size) {
            playersMap[player]!!.votesAmount = 0
        }
    }

    private fun killThePlayer(playerNumber: Int) {
        playersMap[playerNumber]!!.alive = false
        killedPlayersList.add(playerNumber)
        isSomebodyKilled = true
    }

    fun getKilledPlayersList(): ArrayList<Int> {
        return killedPlayersList
    }

    fun chooseThePlayer(playerNumber: Int) {
        when (currentPart) {
            GameFlowConstants.MAFIA_KILLS -> {
                playersMap[playerNumber]!!.isToBeDead = true
            }
            GameFlowConstants.MISTRESS_PAYS_A_VISIT -> {
                playersMap[playerNumber]!!.isToBeBlocked = true
                playersMap[playerNumber]!!.blocksStreak++
                removeBlockStreaks(playerNumber)
            }
            GameFlowConstants.DOCTOR_HEALS -> {
                playersMap[playerNumber]!!.isToBeHealed = true
                playersMap[playerNumber]!!.wasHealedByDoctorTheLastNight = true
                removeHealStreaks(playerNumber)
            }
            GameFlowConstants.MANIAC_KILLS -> {
                playersMap[playerNumber]!!.isToBeDead = true
            }
            GameFlowConstants.SHERIFF_CHECKS -> {
                playersMap[playerNumber]!!.isChecked = true
            }
        }
        goToTheNextPart()
    }

    private fun removeHealStreaks(playerToBeLeft: Int) {
        for (player in 1..playersMap.size) {
            if (player != playerToBeLeft) {
                playersMap[player]!!.wasHealedByDoctorTheLastNight = false
            }
        }
    }

    private fun removeBlockStreaks(playerToBeLeft: Int) {
        for (player in 1..playersMap.size) {
            if (player != playerToBeLeft) {
                playersMap[player]!!.blocksStreak = 0
            }
        }
    }

    //returns true only if player of asked role exists AND is alive
    fun isPlayerWithRolePlaying(role: Int): Boolean {
        var isPlaying = false
        for (player in 1..playersMap.size) {
            val player = playersMap[player]
            if (player!!.role == role && player.alive) {
                isPlaying = true
            }
        }
        return isPlaying
    }

    fun findPlayerWithRole(role: Int): Int {
        var playerWithRole = 1
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.role == role) {
                playerWithRole = player
            }
        }
        return playerWithRole
    }

    private fun isVoting(): Boolean {
        return currentPart == GameFlowConstants.LAST_WORDS_AFTER_VOTING
    }

    //Since I want killing roles to be able to kill themselves
    //I return every alive player for these roles
    fun isPlayerMustBeShown(playerNumber: Int, currentRole: Int): Boolean {
        return when (currentRole) {
            Constants.MAFIA -> {
                true
            }
            Constants.MISTRESS -> {
                checkPlayerForMistress(playerNumber)
            }
            Constants.DOCTOR -> {
                checkPlayerForDoctor(playerNumber)
            }
            Constants.MANIAC -> {
                true
            }
            Constants.SHERIFF -> {
                checkPlayerForSheriff(playerNumber)
            }
            else -> true
        }
    }

    private fun checkPlayerForMistress(playerNumber: Int): Boolean {
        return !(playersMap[playerNumber]!!.blocksStreak == 2 ||
                playersMap[playerNumber]!!.role == Constants.MISTRESS)
    }

    private fun checkPlayerForDoctor(playerNumber: Int): Boolean {
        return !(playersMap[playerNumber]!!.wasHealedByDoctorTheLastNight ||
                playersMap[playerNumber]!!.role == Constants.DOCTOR)
    }

    private fun checkPlayerForSheriff(playerNumber: Int): Boolean {
        return !(playersMap[playerNumber]!!.isChecked ||
                playersMap[playerNumber]!!.role == Constants.SHERIFF)
    }

    fun checkIfPlayerIsAlive(playerNumber: Int): Boolean {
        return playersMap[playerNumber]!!.alive
    }

    private fun applyTheNightResults() {
        for (player in 1 until playersMap.size) {
            val playerData = playersMap[player]
            if (playerData!!.isToBeDead && !playerData.isToBeHealed && !playerData.alive) {
                playerData.alive = false
                killedPlayersList.add(getAlivePlayersNumbersArrayList()[player])
            }
        }
    }
}