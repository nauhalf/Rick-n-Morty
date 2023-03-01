package com.nauhalf.ricknmorty.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nauhalf.ricknmorty.R
import com.nauhalf.ricknmorty.core.base.activity.BaseActivity
import com.nauhalf.ricknmorty.core.base.response.HttpError
import com.nauhalf.ricknmorty.core.base.response.NoInternetError
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.core.base.response.TimeOutError
import com.nauhalf.ricknmorty.data.character.api.model.Character
import com.nauhalf.ricknmorty.databinding.ActivityDetailBinding
import com.nauhalf.ricknmorty.di.GlideApp
import com.nauhalf.ricknmorty.util.id
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private val viewModel by viewModels<DetailViewModel>()

    private val characterDetailAdapter by lazy {
        CharacterDetailAdapter()
    }

    override fun onViewBindingInflate(layoutInflater: LayoutInflater): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
        collectData()
    }

    private fun collectData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.detailCharacter.collect {
                        when (it) {
                            RickMortyResponse.Empty -> handleEmpty()
                            is RickMortyResponse.Error -> it.let(::handleError)
                            RickMortyResponse.Loading -> handleLoading()
                            is RickMortyResponse.Success -> it.data.let(::handleSuccess)
                        }
                    }
                }

                launch {
                    viewModel.detailList.collect(characterDetailAdapter::submitList)
                }
            }
        }
    }

    private fun handleEmpty() {
        with(binding) {
            tvError.isVisible = false
            pbDetail.isVisible = false
            scrollDetail.isVisible = false
        }
    }

    private fun handleError(error: RickMortyResponse.Error) {
        val message = when (error) {
            is HttpError -> error.message
            is NoInternetError, is TimeOutError -> getString(R.string.internet_error)
            else -> getString(R.string.general_error)
        }
        with(binding) {
            tvError.isVisible = true
            pbDetail.isVisible = false
            scrollDetail.isVisible = false
            tvError.text = message
        }
    }

    private fun handleLoading() {
        with(binding) {
            tvError.isVisible = false
            pbDetail.isVisible = true
            scrollDetail.isVisible = false
        }
    }

    private fun handleSuccess(data: Character) {
        with(binding) {
            tvError.isVisible = false
            pbDetail.isVisible = false
            scrollDetail.isVisible = true
            GlideApp.with(ivCharacter)
                .load(data.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivCharacter)

            tvName.text = data.name
            if (viewModel.detailFirstEpisode.value !is RickMortyResponse.Success) {
                data.episode.firstOrNull()?.id?.let(viewModel::loadEpisode)
            }

            val bio = listOf<CharacterDetailUiModel>(
                CharacterDetailUiModel.Information(
                    title = R.string.species,
                    value = listOf(data.species, data.type).filter { it.isNotEmpty() }
                        .joinToString(", ")
                ),
                CharacterDetailUiModel.Information(
                    title = R.string.status,
                    value = data.status,
                ),
                CharacterDetailUiModel.Information(
                    title = R.string.gender,
                    value = data.gender,
                ),
                CharacterDetailUiModel.Information(
                    title = R.string.place_of_origin,
                    value = data.origin,
                ),
                CharacterDetailUiModel.Information(
                    title = R.string.home_planet,
                    value = data.location
                ),
            )

            val meta = mutableListOf<CharacterDetailUiModel>(
                CharacterDetailUiModel.Information(
                    title = R.string.total_episode,
                    value = getString(R.string.episode_d, data.episode.size)
                ),
            )

            if (data.episode.isNotEmpty()) {
                meta.add(
                    CharacterDetailUiModel.Information(
                        title = R.string.episode,
                        value = data.episode.joinToString(separator = "\n") {
                            getString(R.string.episode_number, it.id)
                        }
                    )
                )
            }

            viewModel.setBiographicalList(bio)
            viewModel.setMetaList(meta)
        }
    }

    private fun setUp() {
        with(binding) {
            rvCharacterDetail.adapter = characterDetailAdapter
            fabBack.setOnClickListener {
                finish()
            }
            tvError.setOnClickListener {
                viewModel.refresh()
            }
        }
    }
}
