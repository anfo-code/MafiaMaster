package com.example.mafiamaster.recyclerviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.RowNightActionItemBinding
import com.example.mafiamaster.modelview.GameModelView

//The entered Hash map, must be a hash map, having only alive players in it
class NightActionItemAdapter(
    val context: Context,
    private val gameModelView: GameModelView,
    private val currentRole: Int
) :
    RecyclerView.Adapter<NightActionItemAdapter.ViewHolder>() {

    private lateinit var binding: RowNightActionItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = RowNightActionItemBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(
            binding.root, binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playerNumber = position + 1
        holder.playersNumberTextView.text = context.getString(R.string.player, playerNumber)
        holder.chooseThePlayer.setOnClickListener {
            gameModelView.chooseThePlayer(playerNumber)
        }
        //Since removing players row is way too complicated
        //To implement, I am just removing possibility to
        //Make some action with unavailable players
        if (gameModelView.isPlayerMustBeShown(playerNumber, currentRole) &&
            gameModelView.checkIfPlayerIsAlive(playerNumber)) {
            holder.chooseThePlayer.visibility = View.VISIBLE
        } else {
            holder.chooseThePlayer.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return gameModelView.getCurrentPlayersMap().size
    }

    inner class ViewHolder(view: View, binding: RowNightActionItemBinding) :
        RecyclerView.ViewHolder(view) {
        val playersNumberTextView = binding.playersNumberTextView
        val chooseThePlayer = binding.chooseThePlayer
    }
}