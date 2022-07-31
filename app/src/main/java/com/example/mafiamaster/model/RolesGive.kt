package com.example.mafiamaster.model

import com.example.mafiamaster.constants.Constants
import kotlin.random.Random

class RolesGive(private val rolesCountHandler: RolesCountHandler) {

    private var playersMap: HashMap<Int, Player> = HashMap()
    private var _playersCount = 0
    private var _mafiaCount = 0
    private var _mistressCount = 0
    private var _donCount = 0
    private var _doctorCount = 0
    private var _maniacCount = 0
    private var _sheriffCount = 0

    //Class sets up all of the data as soon, as it was created
    //The only operation possible with the class is getting
    //The prepared players map with roles
    init {
        setData()
        createHashMap()
        assignRoles()
    }

    private fun setData() {
        _playersCount = rolesCountHandler.getPlayersCount()
        _mafiaCount = rolesCountHandler.getMafiaCount()
        _mistressCount = rolesCountHandler.getMistressCount()
        _donCount = rolesCountHandler.getDonCount()
        _doctorCount = rolesCountHandler.getDoctorCount()
        _maniacCount = rolesCountHandler.getManiacCount()
        _sheriffCount = rolesCountHandler.getSheriffCount()
    }

    fun getPlayersMap(): HashMap<Int, Player> {
        return playersMap
    }

    private fun createHashMap() {
        for (player in 1.._playersCount) {
            playersMap[player] = Player()
        }
    }

    private fun assignRoles() {
        for (mafia in 1.._mafiaCount) {
            assignSpecificRole(Constants.MAFIA)
        }
        if (_mistressCount == 1){
            assignSpecificRole(Constants.MISTRESS)
        }
        if (_donCount == 1){
            assignSpecificRole(Constants.DON)
        }
        if (_doctorCount == 1){
            assignSpecificRole(Constants.DOCTOR)
        }
        if (_maniacCount == 1){
            assignSpecificRole(Constants.MANIAC)
        }
        if (_sheriffCount == 1){
            assignSpecificRole(Constants.SHERIFF)
        }
    }

    private fun assignSpecificRole(role: Int) {
        val playerToBeAssigned = Random.nextInt(1, _playersCount)
        val chosenPlayerData = playersMap[playerToBeAssigned]
        //Check if chosen player has a role already
        //If he does, a recursive call will be made
        if (chosenPlayerData!!.role == Constants.CIVILIAN) {
            chosenPlayerData.role = role
            playersMap.replace(playerToBeAssigned, chosenPlayerData)
        } else {
            assignSpecificRole(role)
        }
    }
}