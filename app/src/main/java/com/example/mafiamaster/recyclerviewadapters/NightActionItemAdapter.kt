package com.example.mafiamaster.recyclerviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mafiamaster.R
import com.example.mafiamaster.databinding.RowNightActionItemBinding
import com.example.mafiamaster.databinding.RowVotingItemBinding
import com.example.mafiamaster.model.GameMaster
import com.example.mafiamaster.model.Player

//The entered Hash map, must be a hash map, having only alive players in it
class NightActionItemAdapter(
    val context: Context,
    private val gameMaster: GameMaster
) :
    RecyclerView.Adapter<NightActionItemAdapter.ViewHolder>() {

    private lateinit var binding: RowNightActionItemBinding
    private var playersMap: HashMap<Int, Player> = HashMap()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = RowNightActionItemBinding.inflate(LayoutInflater.from(context))
        playersMap = gameMaster.getAlivePlayersMap()
        return ViewHolder(
            binding.root, binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = getPlayer(position)
        val playerNumber = getPlayerNumber(position)
        holder.playersNumberTextView.text = context.getString(R.string.player, playerNumber)
        holder.chooseThePlayer.setOnClickListener{
            gameMaster.chooseThePlayer(playerNumber)
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

    inner class ViewHolder(view: View, binding: RowNightActionItemBinding) : RecyclerView.ViewHolder(view) {
        val playersNumberTextView = binding.playersNumberTextView
        val chooseThePlayer = binding.chooseThePlayer

    }
}