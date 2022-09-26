package com.example.mafiamaster.model

import com.example.mafiamaster.constants.GameFlowConstants

data class GameData (
    var playersMap: HashMap<Int, Player>,
    var isGameOver: Boolean = false,
    var currentPart: Int = GameFlowConstants.NIGHT_OF_GETTING_ACQUAINTANCES,
    var isSomebodyKilled: Boolean = false,
    var isFirstSpeech: Boolean = true,
    var killedPlayersList: ArrayList<Int> = ArrayList(),
    var playerSpeaking: Int = 1,
    var round: Int = 1,
    var playerSpeakingCount: Int = 1,
    var winner: Int = 0,
)