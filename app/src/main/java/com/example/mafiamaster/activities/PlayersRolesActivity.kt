package com.example.mafiamaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mafiamaster.databinding.ActivityPlayersRolesBinding

class PlayersRolesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPlayersRolesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPlayersRolesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolbar()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarMain.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}