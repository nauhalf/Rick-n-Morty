package com.nauhalf.ricknmorty.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nauhalf.ricknmorty.R
import com.nauhalf.ricknmorty.data.character.api.model.Character
import com.nauhalf.ricknmorty.databinding.ItemCharacterBinding
import com.nauhalf.ricknmorty.di.GlideApp
import com.nauhalf.ricknmorty.util.onItemCallback

class CharacterAdapter(private val onCharacterClicked: (Character) -> Unit) :
    ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(
        onItemCallback({ oldItem, newItem ->
            oldItem.id == newItem.id
        }, { oldItem, newItem ->
            oldItem == newItem
        })
    ) {
    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Character) {
            with(binding) {
                tvCharacterName.text = data.name
                tvCharacterStatus.text =
                    root.resources.getString(R.string.species_status, data.species, data.status)
                GlideApp.with(ivCharacter)
                    .load(data.image)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivCharacter)
                root.setOnClickListener {
                    onCharacterClicked(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        return holder.bind(currentList[position])
    }

}
