package com.example.mafiamaster.model

import android.util.Log
import com.example.mafiamaster.activities.GameActivity
import com.example.mafiamaster.constants.Constants
import com.example.mafiamaster.constants.GameFlowConstants

open class GameModel(
    private val playersMap: HashMap<Int, Player>,
    private val activity: GameActivity
    ) {

    protected lateinit var gameData : GameData

    init {
        setGameData()
    }

    private fun setGameData() {
        gameData = GameData(playersMap)
    }

    fun getCurrentGameData(): GameData {
        return gameData
    }

    protected fun setTheNextPart() {
        gameData.currentPart++
    }

    protected fun setNightPart() {
        gameData.currentPart = GameFlowConstants.NIGHT
    }

    protected fun setTalkPart() {
        gameData.currentPart = GameFlowConstants.TALK
    }

    protected fun getAlivePlayersAmount(): Int {
        var alivePlayersAmount = 0
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.alive) {
                alivePlayersAmount++
            }
        }
        return alivePlayersAmount
    }

    //this function gets an alive player by playerSpeaking count
    //if current playerSpeaker is 3, it gets third alive player in the list
    protected fun getPlayerByCount(): Int {
        val playerFromCount: Int

        val alivePlayersList = getAlivePlayersNumbersArrayList()
        var playerInArray = gameData.playerSpeakingCount

        if (playerInArray > alivePlayersList.size - 1) {
            playerInArray = gameData.playerSpeakingCount - alivePlayersList.size
        }

        Log.i("PLAYER", playerInArray.toString())
        playerFromCount = alivePlayersList[playerInArray]

        gameData.playerSpeakingCount++

        return playerFromCount
    }

    private fun getPlayerWithMostVotes(): Int {
        //Start comparing from the first alive player
        var numberOfPlayerWithMostVotes = 1
        for (player in 1 until playersMap.size + 1) {
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

    private fun getAlivePlayersNumbersArrayList(): ArrayList<Int> {
        val alivePlayersNumberArrayList: ArrayList<Int> = ArrayList()
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.alive) {
                alivePlayersNumberArrayList.add(player)
            }
        }
        return alivePlayersNumberArrayList
    }

    //returns true only if player of asked role exists AND is alive
    fun getIfAPlayerWithRolePlaying(role: Int): Boolean {
        var isPlaying = false
        for (player in 1..playersMap.size) {
            val playerInfo = playersMap[player]
            if (playerInfo!!.role == role && playerInfo.alive) {
                isPlaying = true
            }
        }
        return isPlaying
    }

    fun getPlayerWithRole(role: Int): Int {
        var playerWithRole = 1
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.role == role) {
                playerWithRole = player
            }
        }
        return playerWithRole
    }

    protected fun getIfCurrentPartIsVoting(): Boolean {
        return gameData.currentPart == GameFlowConstants.LAST_WORDS_AFTER_VOTING
    }

    fun addVoteToPlayer(player: Int) {
        playersMap[player]!!.votesAmount++
    }

    fun removeVoteFromPlayer(player: Int) {
        if (playersMap[player]!!.votesAmount > 0) {
            playersMap[player]!!.votesAmount--
        }
    }

    fun executePlayerWithMostVotes() {
        val playerToBeExecuted = getPlayerWithMostVotes()
        clearTheVotes()
        killThePlayer(playerToBeExecuted)
        if (checkIfTheGameIsFinished()) {
            activity.finishTheGame()
        }
    }

    private fun clearTheVotes() {
        for (player in 1..playersMap.size) {
            playersMap[player]!!.votesAmount = 0
        }
    }

    private fun killThePlayer(playerNumber: Int) {
        playersMap[playerNumber]!!.alive = false
        gameData.killedPlayersList.add(playerNumber)
        gameData.isSomebodyKilled = true
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

    fun chooseThePlayer(playerNumber: Int) {
        when (gameData.currentPart) {
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
        setTheNextPart()
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

    protected fun sumUpNightResult() {
        for (player in 1 until gameData.playersMap.size) {
            val playerData = gameData.playersMap[player]
            if (playerData!!.isToBeDead && !playerData.isToBeHealed && playerData.alive) {
                playerData.alive = false
                gameData.killedPlayersList.add(player)
                Log.i("DEAD", player.toString())
            }
        }
        if (checkIfTheGameIsFinished()) {
            activity.finishTheGame()
        }
    }

    //returns that the game is finished if amount of black players equals
    //the amount of red players and if maniac and doctor are not on the table
    private fun checkIfTheGameIsFinished(): Boolean {
        var amountOfBlackPlayers = 0
        for (player in 1..playersMap.size) {
            if (playersMap[player]!!.alive) {
                if (playersMap[player]!!.role == Constants.MAFIA ||
                    playersMap[player]!!.role == Constants.DON ||
                    playersMap[player]!!.role == Constants.MISTRESS
                ) {
                    amountOfBlackPlayers++
                }
            }
        }
        val amountOfRedPlayers = getAlivePlayersAmount() - amountOfBlackPlayers
        if (amountOfRedPlayers / amountOfBlackPlayers == 1 &&
            amountOfRedPlayers % amountOfBlackPlayers == 0 &&
            getIfAPlayerWithRolePlaying(Constants.MANIAC) &&
            getIfAPlayerWithRolePlaying(Constants.DOCTOR)
        ) {
            gameData.winner = Constants.MAFIA_WINNER
            return true
        }
        if (amountOfBlackPlayers == 0) {
            gameData.winner = Constants.CIVILIANS_WINNER
            return true
        }
        return false
    }
}