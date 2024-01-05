package com.agungtriu.themeal.ui.bookmark

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.agungtriu.themeal.databinding.FragmentBookmarkBinding
import com.agungtriu.themeal.ui.base.BaseFragment
import com.agungtriu.themeal.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(FragmentBookmarkBinding::inflate) {
    private val viewModel: BookmarkViewModel by viewModels()
    private lateinit var adapter: BookmarkAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BookmarkAdapter(viewModel, findNavController())
        binding.rvBookmark.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBookmark.adapter = adapter
        setUpObserverData()
        binding.swiperBookmark.setOnRefreshListener {
            viewModel.selectMeals()
        }

        binding.includeBookmarkError.btnErrorResetRefresh.setOnClickListener {
            viewModel.selectMeals()
        }

        binding.toolbarBookmark.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpObserverData() {
        viewModel.resultSelect.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.shimmerBookmark.startShimmer()
                    binding.shimmerBookmark.visibility = View.VISIBLE
                    binding.rvBookmark.visibility = View.GONE
                    binding.includeBookmarkError.constraintError.visibility = View.GONE
                }

                is ViewState.Error -> {
                    binding.shimmerBookmark.stopShimmer()
                    binding.shimmerBookmark.visibility = View.GONE
                    binding.swiperBookmark.isRefreshing = false
                    binding.includeBookmarkError.constraintError.visibility = View.VISIBLE
                    binding.includeBookmarkError.tvErrorDesc.text = it.error
                    binding.includeBookmarkError.btnErrorResetRefresh.visibility = View.GONE
                }

                is ViewState.Success -> {
                    binding.rvBookmark.visibility = View.VISIBLE
                    binding.shimmerBookmark.stopShimmer()
                    binding.shimmerBookmark.visibility = View.GONE
                    binding.swiperBookmark.isRefreshing = false
                    adapter.submitList(it.data)
                }
            }
        }

        viewModel.resultDelete.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {}

                is ViewState.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }

                is ViewState.Success -> {}
            }
        }
    }
}