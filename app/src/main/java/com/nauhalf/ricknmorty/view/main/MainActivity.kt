package com.nauhalf.ricknmorty.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nauhalf.ricknmorty.R
import com.nauhalf.ricknmorty.core.base.activity.BaseActivity
import com.nauhalf.ricknmorty.core.base.response.HttpError
import com.nauhalf.ricknmorty.core.base.response.NoInternetError
import com.nauhalf.ricknmorty.core.base.response.RickMortyResponse
import com.nauhalf.ricknmorty.core.base.response.TimeOutError
import com.nauhalf.ricknmorty.data.character.api.model.Character
import com.nauhalf.ricknmorty.databinding.ActivityMainBinding
import com.nauhalf.ricknmorty.util.GridSpacingItemDecoration
import com.nauhalf.ricknmorty.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()
    private val characterAdapter by lazy {
        CharacterAdapter {
            startActivity(Intent(this, DetailActivity::class.java).apply {
                putExtra("id", it.id)
            })
        }
    }

    override fun onViewBindingInflate(layoutInflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
        collectData()
    }

    private fun collectData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.character.collect {
                        when (it) {
                            RickMortyResponse.Empty -> handleEmpty()
                            is RickMortyResponse.Error -> it.let(::handleError)
                            RickMortyResponse.Loading -> handleLoading()
                            is RickMortyResponse.Success -> it.data.let(::handleSuccess)
                        }
                    }
                }
            }
        }
    }

    private fun handleEmpty() {
        with(binding) {
            tvError.isVisible = false
            rvCharacter.isVisible = false
            pbCharacter.isVisible = false
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
            tvError.text = getString(R.string.error_tap_to_retry, message)
            rvCharacter.isVisible = false
            pbCharacter.isVisible = false
            swipeRefresh.isRefreshing = false
        }
    }

    private fun handleLoading() {
        with(binding) {
            tvError.isVisible = false
            rvCharacter.isVisible = false
            pbCharacter.isVisible = true
        }
    }

    private fun handleSuccess(data: List<Character>) {
        with(binding) {
            tvError.isVisible = false
            rvCharacter.isVisible = true
            pbCharacter.isVisible = false
            swipeRefresh.isRefreshing = false
            characterAdapter.submitList(data)
        }
    }

    private fun setUp() {
        with(binding) {
            rvCharacter.adapter = characterAdapter
            if (rvCharacter.itemDecorationCount == 0) {
                rvCharacter.addItemDecoration(
                    GridSpacingItemDecoration(
                        spanCount = 2,
                        spacing = resources.getDimensionPixelOffset(R.dimen.dp_8),
                        includeEdge = false
                    )
                )
            }

            tvError.setOnClickListener {
                viewModel.loadAllCharacter()
            }

            swipeRefresh.setOnRefreshListener {
                viewModel.loadAllCharacter()
            }

        }
    }
}
