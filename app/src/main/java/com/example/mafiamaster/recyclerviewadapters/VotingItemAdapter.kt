package com.example.mafiamaster.recyclerviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.RowVotingItemBinding
import com.example.mafiamaster.model.GameMaster
import com.example.mafiamaster.model.Player

//The entered Hash map, must be a hash map, having only alive players in it
class VotingItemAdapter(
    val context: Context,
    private val gameMaster: GameMaster
) :
    RecyclerView.Adapter<VotingItemAdapter.ViewHolder>() {

    private lateinit var binding: RowVotingItemBinding
    private var playersMap: HashMap<Int, Player> = HashMap()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = RowVotingItemBinding.inflate(LayoutInflater.from(context))
        playersMap = gameMaster.getAlivePlayersMap()
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
            gameMaster.addVoteToPlayer(playerNumber)
            notifyItemChanged(position)
        }
        holder.ivRemoveVotes.setOnClickListener {
            gameMaster.removeVoteFromPlayer(playerNumber)
            notifyItemChanged(position)
        }
        if (gameMaster.checkIfPlayerIsAlive(playerNumber)) {
            holder.clChooseVotesAmount.visibility = View.VISIBLE
        } else {
            holder.clChooseVotesAmount.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return gameMaster.getCurrentPlayersMap().size
    }

    private fun getPlayer(playerNumber: Int): Player{
        return gameMaster.getCurrentPlayersMap()[playerNumber]!!
    }

    inner class ViewHolder(view: View, binding: RowVotingItemBinding) : RecyclerView.ViewHolder(view) {
        val playersNumberTextView = binding.playersNumberTextView
        val votesCountTextView = binding.votesCountTextView
        val ivAddVotes = binding.ivAddVotesTextView
        val ivRemoveVotes = binding.ivRemoveVotes
        val clChooseVotesAmount = binding.clChooseVotesAmount
    }
}