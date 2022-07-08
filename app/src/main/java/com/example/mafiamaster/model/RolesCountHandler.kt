package com.example.mafiamaster.model

class RolesCountHandler {
    private var _playersCount = 6
    private var _mafiaCount = 2
    private var _mistressCount = 0
    private var _donCount = 0
    private var _doctorCount = 0
    private var _maniacCount = 0
    private var _sheriffCount = 0

    fun addPlayer() {
        if (_playersCount in 6..11){
            _playersCount++
            if (_playersCount / 3 > _mafiaCount + _donCount + _mistressCount) {
                addMafia()
            }
        }
    }

    fun removePlayer() {
        if (_playersCount in 7..12){
            _playersCount--
            if (_playersCount / 3 < _mafiaCount + _donCount + _mistressCount) {
                removeMafia()
            }
        }
    }

    private fun addMafia() {
        _mafiaCount++
        if (_mafiaCount > 1) {
            addDonMafia()
        }
    }

    private fun removeMafia() {
        _mafiaCount--
        if (_mafiaCount == 1) {
            removeDonMafia()
        }
    }

    private fun addDonMafia() {
        if (_donCount == 0) {
            _donCount++
            _mafiaCount--
        }
    }

    private fun removeDonMafia() {
        if (_donCount == 1) {
            _donCount--
            _mafiaCount++
        }
    }

    fun addMistress() {
        if (_mistressCount == 0) {
            _mistressCount++
            removeMafia()
        }
    }

    fun removeMistress() {
        if (_mistressCount == 1) {
            _mistressCount--
            addMafia()
        }
    }

    fun addDoctor() {
        if (_doctorCount == 0) {
            _doctorCount++
        }
    }

    fun removeDoctor() {
        if (_doctorCount == 1) {
            _doctorCount--
        }
    }

    fun addSheriff() {
        if (_sheriffCount == 0) {
            _sheriffCount++
        }
    }

    fun removeSheriff() {
        if (_sheriffCount == 1) {
            _sheriffCount--
        }
    }

    fun addManiac() {
        if (_maniacCount == 0) {
            _maniacCount++
        }
    }

    fun removeManiac() {
        if (_maniacCount == 1) {
            _maniacCount--
        }
    }

    fun getPlayersCount(): Int {
        return _playersCount
    }

    fun getMafiaCount(): Int {
        return _mafiaCount
    }

    fun getDonCount(): Int {
        return _donCount
    }

    fun getMistressCount(): Int {
        return _mistressCount
    }

    fun getDoctorCount(): Int {
        return _doctorCount
    }

    fun getSheriffCount(): Int {
        return _sheriffCount
    }

    fun getManiacCount(): Int {
        return _maniacCount
    }
}