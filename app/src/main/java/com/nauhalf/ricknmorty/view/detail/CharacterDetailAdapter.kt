package com.nauhalf.ricknmorty.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nauhalf.ricknmorty.databinding.ItemCharacterDetailInformationBinding
import com.nauhalf.ricknmorty.databinding.ItemCharacterDetailSeparatorBinding
import com.nauhalf.ricknmorty.util.onItemCallback

class CharacterDetailAdapter : ListAdapter<CharacterDetailUiModel, RecyclerView.ViewHolder>(
    onItemCallback({ oldItem, newItem ->
        oldItem.title == newItem.title
    }, { oldItem, newItem ->
        oldItem == newItem
    })
) {

    companion object {
        private const val ITEM_SEPARATOR = 1
        private const val ITEM_INFORMATION = 2
    }

    class SeparatorViewHolder(private val binding: ItemCharacterDetailSeparatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CharacterDetailUiModel.Separator) {
            binding.root.text = binding.root.resources.getString(data.title)
        }
    }

    class InformationViewHolder(private val binding: ItemCharacterDetailInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CharacterDetailUiModel.Information) {
            with(binding) {
                tvInfoTitle.text = root.resources.getString(data.title)
                tvInfoValue.text = data.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_SEPARATOR -> SeparatorViewHolder(
                ItemCharacterDetailSeparatorBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> InformationViewHolder(
                ItemCharacterDetailInformationBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SeparatorViewHolder -> (currentList[position] as?
                    CharacterDetailUiModel.Separator)?.let(holder::bind)
            is InformationViewHolder -> (currentList[position] as?
                    CharacterDetailUiModel.Information)?.let(holder::bind)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is CharacterDetailUiModel.Separator -> ITEM_SEPARATOR
            is CharacterDetailUiModel.Information -> ITEM_INFORMATION
        }
    }
}
