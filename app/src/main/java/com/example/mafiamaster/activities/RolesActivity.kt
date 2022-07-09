package com.example.mafiamaster.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.ActivityRolesBinding
import com.example.mafiamaster.model.Player
import com.example.mafiamaster.model.RolesGive
import com.example.mafiamaster.utils.BaseForActivities
import com.example.mafiamaster.utils.Constants

class RolesActivity : BaseForActivities(), View.OnClickListener {

    private lateinit var binding: ActivityRolesBinding
    private var playersMap: HashMap<Int, Player> = HashMap()
    private var isRoleShown = false
    private var currentPlayer = 0
    private var isTheLastPlayerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRolesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        hideRole()
        goToTheNextPlayer()

        getPlayersMapFromIntent()

        binding.roleButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.roleButton -> {
                isRoleShown = if (isRoleShown) {
                    if (isTheLastPlayerShown) {
                        startTheGame()
                    } else {
                        hideRole()
                        goToTheNextPlayer()
                    }
                    false
                } else {
                    showRole()
                    true
                }
            }
        }
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }

    private fun getPlayersMapFromIntent() {
        playersMap = intent.getSerializableExtra(Constants.ROLES_MAP_EXTRA_KEY) as HashMap<Int, Player>
    }

    private fun hideRole() {
        binding.roleIconImageView.setImageResource(R.drawable.ic_question_mark)
        binding.mainTextView.text = getString(R.string.your_role_is)
        binding.roleButton.text = getString(R.string.show)
    }

    private fun goToTheNextPlayer() {
        currentPlayer++
        if (currentPlayer == playersMap.size){
            isTheLastPlayerShown = true
        }
        binding.playerNumberTextView.text = getString(R.string.player, currentPlayer)
    }

    private fun showRole() {
        when (playersMap[currentPlayer]!!.role) {
            Constants.CIVILIAN -> {
                binding.roleIconImageView.setImageResource(R.drawable.ic_civillian)
                binding.mainTextView.text = getString(R.string.civilian)
            }
            Constants.MAFIA -> {
                binding.roleIconImageView.setImageResource(R.drawable.ic_mafia)
                binding.mainTextView.text = getString(R.string.mafia)
            }
            Constants.DON -> {
                binding.roleIconImageView.setImageResource(R.drawable.ic_don)
                binding.mainTextView.text = getString(R.string.don)
            }
            Constants.MISTRESS -> {
                binding.roleIconImageView.setImageResource(R.drawable.ic_mistress)
                binding.mainTextView.text = getString(R.string.mistress)
            }
            Constants.DOCTOR -> {
                binding.roleIconImageView.setImageResource(R.drawable.ic_doctor)
                binding.mainTextView.text = getString(R.string.doctor)
            }
            Constants.MANIAC -> {
                binding.roleIconImageView.setImageResource(R.drawable.ic_maniac)
                binding.mainTextView.text = getString(R.string.maniac)
            }
            Constants.SHERIFF -> {
                binding.roleIconImageView.setImageResource(R.drawable.ic_sheriff)
                binding.mainTextView.text = getString(R.string.sheriff)
            }
        }
        binding.roleButton.text = getString(R.string.next_player)
    }

    private fun startTheGame() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(Constants.ROLES_MAP_EXTRA_KEY, playersMap)
        startActivity(intent)
    }
}