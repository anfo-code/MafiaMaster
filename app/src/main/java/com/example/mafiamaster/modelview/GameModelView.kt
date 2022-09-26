package com.example.mafiamaster.modelview

import com.example.mafiamaster.activities.GameActivity
import com.example.mafiamaster.constants.GameFlowConstants
import com.example.mafiamaster.model.GameModel
import com.example.mafiamaster.model.Player

class GameModelView(
    playersMap: HashMap<Int, Player>,
    private val activity: GameActivity,
) : GameModel(playersMap, activity) {


    init {
        startCurrentPart()
    }

    private fun startCurrentPart() {
        when (gameData.currentPart) {
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
                sumUpNightResult()
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
        if (gameData.isGameOver) {
            finishTheGame()
        } else {
            if (gameData.currentPart == GameFlowConstants.LAST_WORDS_AFTER_VOTING) {
                setNightPart()
                startCurrentPart()
            } else {
                setTheNextPart()
                startCurrentPart()
            }
        }
    }

    fun timerFinished() {
        when (gameData.currentPart) {
            GameFlowConstants.TALK -> {
                goToTheNextPart()
            }
            GameFlowConstants.SPEECHES -> {
                if (gameData.playerSpeakingCount == getAlivePlayersAmount() + gameData.round - 1) {
                    gameData.playerSpeakingCount -= getAlivePlayersAmount() - gameData.round
                    gameData.round++
                    goToTheNextPart()
                } else {
                    nextPlayerSpeech()
                }
            }
        }
    }

    fun goToThePartAfterGettingAcquaintances() {
        setTalkPart()
        startCurrentPart()
    }

    private fun startNightOfGettingAcquaintances() {
        activity.hideAllActions()
        activity.showNightOfGettingAcquaintancesAction()
    }

    private fun startTalk() {
        activity.hideAllActions()
        activity.showTalkAction()
        gameData.killedPlayersList = ArrayList()
    }

    private fun startSpeeches() {
        if (gameData.isFirstSpeech) {
            setFirstSpeaker()
        } else {
            nextPlayerSpeech()
        }
        activity.startCurrentPlayerSpeech()
    }

    private fun nextPlayerSpeech() {
        gameData.playerSpeaking = getPlayerByCount()
        activity.startCurrentPlayerSpeech()
    }

    private fun setFirstSpeaker() {
        for (player in gameData.round until gameData.playersMap.size) {
            if (gameData.playersMap[player]!!.alive) {
                gameData.playerSpeaking = player
                break
            }
        }
        gameData.isFirstSpeech = false
    }

    private fun startVoting() {
        activity.hideAllActions()
        activity.showVotingAction()
    }

    private fun startLastWords() {
        activity.hideAllActions()
        activity.showKilledPlayersRoleAction(getIfCurrentPartIsVoting())
    }

    private fun startNight() {
        gameData.killedPlayersList = ArrayList()
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
    }

    private fun finishTheGame() {

    }
}