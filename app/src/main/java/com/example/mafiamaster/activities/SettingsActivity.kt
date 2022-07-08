package com.example.mafiamaster.activities

import android.os.Bundle
import android.view.View
import com.example.mafiamaster.databinding.ActivitySettingsBinding
import com.example.mafiamaster.utils.BaseForActivities

class SettingsActivity : BaseForActivities(), View.OnClickListener {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingsBinding.inflate(layoutInflater)
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