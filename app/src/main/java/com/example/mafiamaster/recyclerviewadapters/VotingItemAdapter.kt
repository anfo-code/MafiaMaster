package com.example.mafiamaster.recyclerviewadapters

import android.content.Context
import android.util.Log
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
        Log.i("PLAYERS", "MUST BE SHOWN")
        return ViewHolder(
            binding.root, binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = getPlayer(position)
        val playerNumber = getPlayerNumber(position)
        Log.i("PLAYERS_NUMBER", "$playerNumber")
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
    }

    override fun getItemCount(): Int {
        return gameMaster.getAlivePlayersNumbersArrayList().size
    }

    private fun getPlayer(position: Int): Player{
        val playerNumber = getPlayerNumber(position)
        return gameMaster.getAlivePlayersMap()[playerNumber]!!
    }

    private fun getPlayerNumber(position: Int): Int{
        return gameMaster.getAlivePlayersNumbersArrayList()[position]
    }

    inner class ViewHolder(view: View, binding: RowVotingItemBinding) : RecyclerView.ViewHolder(view) {
        val playersNumberTextView = binding.playersNumberTextView
        val votesCountTextView = binding.votesCountTextView
        val ivAddVotes = binding.ivAddVotesTextView
        val ivRemoveVotes = binding.ivRemoveVotes
    }
}