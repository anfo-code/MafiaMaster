package com.example.mafiamaster.activities

import android.os.Bundle
import android.view.View
import com.example.mafiamaster.databinding.ActivityGameBinding
import com.example.mafiamaster.utils.BaseForActivities

class GameActivity : BaseForActivities(), View.OnClickListener {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}