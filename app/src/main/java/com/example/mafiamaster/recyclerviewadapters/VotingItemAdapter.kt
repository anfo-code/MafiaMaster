package com.example.mafiamaster.recyclerviewadapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.RowVotingItemBinding
import com.example.mafiamaster.modelview.GameModelView
import com.example.mafiamaster.model.Player

//The entered Hash map, must be a hash map, having only alive players in it
class VotingItemAdapter(
    val context: Context,
    private val gameModelView: GameModelView
) :
    RecyclerView.Adapter<VotingItemAdapter.ViewHolder>() {

    private lateinit var binding: RowVotingItemBinding
    private var playersMap: HashMap<Int, Player> = HashMap()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = RowVotingItemBinding.inflate(LayoutInflater.from(context))
        playersMap = gameModelView.getCurrentGameData().playersMap
        return ViewHolder(
            binding.root, binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playerNumber = position + 1
        val player = getPlayer(playerNumber)
        holder.playersNumberTextView.text = context.getString(R.string.player, playerNumber)
        holder.votesCountTextView.text = player.votesAmount.toString()
        holder.ivAddVotes.setOnClickListener {
            gameModelView.addVoteToPlayer(playerNumber)
            Log.i("ADD VOTE TO PLAYER", playerNumber.toString())
            notifyItemChanged(position)
        }
        holder.ivRemoveVotes.setOnClickListener {
            gameModelView.removeVoteFromPlayer(playerNumber)
            notifyItemChanged(position)
        }
        if (gameModelView.checkIfPlayerIsAlive(playerNumber)) {
            holder.clChooseVotesAmount.visibility = View.VISIBLE
        } else {
            holder.clChooseVotesAmount.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return gameModelView.getCurrentGameData().playersMap.size
    }

    private fun getPlayer(playerNumber: Int): Player{
        return gameModelView.getCurrentGameData().playersMap[playerNumber]!!
    }

    inner class ViewHolder(view: View, binding: RowVotingItemBinding) : RecyclerView.ViewHolder(view) {
        val playersNumberTextView = binding.playersNumberTextView
        val votesCountTextView = binding.votesCountTextView
        val ivAddVotes = binding.ivAddVotesTextView
        val ivRemoveVotes = binding.ivRemoveVotes
        val clChooseVotesAmount = binding.clChooseVotesAmount
    }
}