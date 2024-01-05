package com.agungtriu.themeal.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.agungtriu.themeal.R
import com.agungtriu.themeal.databinding.FragmentMainBinding
import com.agungtriu.themeal.ui.base.BaseFragment
import com.agungtriu.themeal.ui.main.filter.BottomSheetFilterFragment
import com.agungtriu.themeal.ui.main.filter.BottomSheetFilterFragment.Companion.FILTER_KEY
import com.agungtriu.themeal.ui.main.filter.BottomSheetFilterFragment.Companion.RESULT_FILTER_KEY
import com.agungtriu.themeal.ui.main.filter.FilterModel
import com.agungtriu.themeal.utils.Utils
import com.agungtriu.themeal.utils.ViewState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    private lateinit var filterBottomSheet: BottomSheetFilterFragment
    private var filterModel: FilterModel = FilterModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MainAdapter(findNavController())
        binding.rvMain.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMain.adapter = adapter
        resultListener()
        setUpObserveData()
        binding.swiperMain.setOnRefreshListener {
            if (!filterModel.area.isNullOrBlank() || !filterModel.category.isNullOrBlank()) {
                viewModel.getFilter(area = filterModel.area, category = filterModel.category)
            } else {
                viewModel.getSearch()
            }
        }

        binding.tietMainSearch.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                viewModel.key = binding.tietMainSearch.text.toString()
                viewModel.getSearch()
                if (!filterModel.area.isNullOrBlank() || !filterModel.category.isNullOrBlank()) {
                    binding.chipgroupMainFilter.removeAllViews()
                    filterModel = FilterModel()
                }
                return@setOnKeyListener true
            }
            false
        }
        binding.chipMainFilter.setOnClickListener {
            binding.tietMainSearch.clearFocus()
            filterBottomSheet = BottomSheetFilterFragment()
            filterBottomSheet.arguments = bundleOf(
                TO_FILTER_KEY to filterModel
            )
            Log.d("TEST", filterModel.toString())
            filterBottomSheet.show(childFragmentManager, BottomSheetFilterFragment.TAG)
        }

        binding.toolbarMain.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btn_main_bookmark -> {
                    findNavController().navigate(R.id.action_mainFragment_to_bookmarkFragment)
                    true
                }

                else -> false
            }
        }
        binding.includeMainError.btnErrorResetRefresh.setOnClickListener {
            if (binding.includeMainError.btnErrorResetRefresh.text == "Reset"){
                filterModel = FilterModel()
                binding.chipgroupMainFilter.removeAllViews()
                binding.tietMainSearch.text = null
                binding.tietMainSearch.clearFocus()
                viewModel.key = ""
                viewModel.getSearch()
            }else {
                if (!filterModel.area.isNullOrBlank() || !filterModel.category.isNullOrBlank()) {
                    viewModel.getFilter(area = filterModel.area, category = filterModel.category)
                } else {
                    viewModel.getSearch()
                }
            }
        }
    }

    private fun resultListener() {
        childFragmentManager.setFragmentResultListener(
            FILTER_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            filterModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(RESULT_FILTER_KEY, FilterModel::class.java)
            } else {
                bundle.getParcelable(RESULT_FILTER_KEY)
            } ?: FilterModel()

            observeFilter(filterModel)
            if (!filterModel.area.isNullOrBlank() || !filterModel.category.isNullOrBlank()) {
                binding.tietMainSearch.text = null
                viewModel.getFilter(area = filterModel.area, category = filterModel.category)
            } else {
                viewModel.getSearch()
            }
        }
    }

    private fun observeFilter(filterModel: FilterModel) {
        binding.chipgroupMainFilter.removeAllViews()
        if (filterModel.area != null) {
            Utils.addChip(binding.chipgroupMainFilter, filterModel.area, requireContext(), false)
        }

        if (filterModel.category != null) {
            Utils.addChip(
                binding.chipgroupMainFilter,
                filterModel.category,
                requireContext(),
                false
            )
        }
    }

    private fun setUpObserveData() {
        viewModel.resultSearch.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    Utils.closeSoftKeyboard(requireView(), requireContext())
                    binding.shimmerMain.startShimmer()
                    binding.shimmerMain.visibility = View.VISIBLE
                    binding.chipMainFilter.visibility = View.GONE
                    binding.chipgroupMainFilter.visibility = View.GONE
                    binding.rvMain.visibility = View.GONE
                    binding.includeMainError.constraintError.visibility = View.GONE
                }

                is ViewState.Error -> {
                    binding.chipMainFilter.visibility = View.VISIBLE
                    binding.chipgroupMainFilter.visibility = View.VISIBLE
                    binding.shimmerMain.stopShimmer()
                    binding.shimmerMain.visibility = View.GONE
                    binding.swiperMain.isRefreshing = false
                    binding.includeMainError.constraintError.visibility = View.VISIBLE
                    if (it.error == "null") {
                        binding.includeMainError.tvErrorDesc.text = "Data Not Found"
                        binding.includeMainError.btnErrorResetRefresh.text = "Reset"
                    } else {
                        binding.includeMainError.tvErrorDesc.text = it.error
                    }
                }

                is ViewState.Success -> {
                    binding.chipMainFilter.visibility = View.VISIBLE
                    binding.chipgroupMainFilter.visibility = View.VISIBLE
                    binding.rvMain.visibility = View.VISIBLE
                    binding.shimmerMain.stopShimmer()
                    binding.shimmerMain.visibility = View.GONE
                    binding.swiperMain.isRefreshing = false
                    adapter.submitList(it.data)
                }
            }
        }
    }

    companion object {
        const val TO_FILTER_KEY = "FilterKey"
    }
}