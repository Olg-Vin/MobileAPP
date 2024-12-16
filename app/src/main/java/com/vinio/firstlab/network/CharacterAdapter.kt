package com.vinio.firstlab.network

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinio.firstlab.databinding.RecyclerItemBinding
import com.vinio.firstlab.entity.Character

class CharacterAdapter(private val characterList: List<Character>)
    : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    class CharacterViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            with(binding) {
                name.text = character.name ?: "-"
                culture.text = character.culture ?: "-"
                born.text = character.born ?: "-"
                titles.text = (character.titles ?: "-").toString()
                aliases.text = (character.aliases ?: "-").toString()
                playedBy.text = (character.playedBy ?: "-").toString()
            }
        }
    }
}
//
//
//if (character.name.isNullOrBlank()) { name.visibility = View.GONE
//} else { name.text = character.name }
//
//if (character.culture.isNullOrBlank()) { culture.visibility = View.GONE
//} else { culture.text = character.culture }
//
//if (character.born.isNullOrBlank()) { born.visibility = View.GONE
//} else { born.text = character.born }
//
//if (character.titles?.isEmpty() == true) { titles.visibility = View.GONE
//} else { titles.text = character.titles.toString()}
//
//if (character.aliases?.isEmpty() == true) { aliases.visibility = View.GONE
//} else { aliases.text = character.aliases.toString()}
//
//if (character.playedBy?.isEmpty() == true) { playedBy.visibility = View.GONE
//} else { playedBy.text = character.playedBy.toString()}
