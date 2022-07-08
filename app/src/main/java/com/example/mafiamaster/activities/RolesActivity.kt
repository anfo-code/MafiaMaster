package com.example.mafiamaster.activities

import android.os.Bundle
import android.view.View
import com.example.mafiamaster.databinding.ActivityRolesBinding
import com.example.mafiamaster.utils.BaseForActivities

class RolesActivity : BaseForActivities(), View.OnClickListener {

    private lateinit var binding: ActivityRolesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRolesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}