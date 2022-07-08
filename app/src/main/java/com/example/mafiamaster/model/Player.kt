package com.example.mafiamaster.model

import com.example.mafiamaster.utils.Constants

data class Player(
    val role: Int = Constants.CIVILIAN,
    var alive: Boolean = true,
    var isToBeDead: Boolean = false,
    var isToBeBlocked: Boolean = false,
    var isToBeHealed: Boolean = false,
    var wasHealedByDoctorTheLastNight: Boolean = false,
    var blocksStreak: Int = 0,
    var foulsCount: Int = 0,
    var isThirdFoulActionCompleted: Boolean = false,
)
