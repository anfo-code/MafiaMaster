package com.example.mafiamaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mafiamaster.databinding.ActivityFoulsBinding

class FoulsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityFoulsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFoulsBinding.inflate(layoutInflater)
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