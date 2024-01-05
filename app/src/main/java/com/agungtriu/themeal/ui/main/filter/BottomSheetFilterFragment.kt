package com.agungtriu.themeal.ui.main.filter

import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.agungtriu.themeal.R
import com.agungtriu.themeal.databinding.BottomSheetFilterBinding
import com.agungtriu.themeal.ui.main.MainFragment.Companion.TO_FILTER_KEY
import com.agungtriu.themeal.utils.Utils
import com.agungtriu.themeal.utils.ViewState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFilterFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilterViewModel by viewModels()

    private var filterModel: FilterModel = FilterModel()

    private var areaStatus = false
    private var categoryStatus = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(requireActivity(), R.style.Theme_TheMeal)
        _binding = BottomSheetFilterBinding.inflate(
            inflater.cloneInContext(contextThemeWrapper),
            container,
            false
        )
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        getArguments(arguments)
    }

    private fun getArguments(arguments: Bundle?) {
        filterModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(TO_FILTER_KEY, FilterModel::class.java)
        } else {
            arguments?.getParcelable(TO_FILTER_KEY)
        } ?: FilterModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val modalBottomSheetBehavior = (dialog as BottomSheetDialog).behavior
        modalBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        setUpListener()
        setUpObserver()
    }

    private fun setUpListener() {
        binding.chipgroupBottomshettfilterArea.setOnCheckedStateChangeListener { group, _ ->
            if (group.checkedChipId == -1) {
                categoryStatus = false
                filterModel.area = null
            } else {
                categoryStatus = true
                val selectedChip = group.findViewById<Chip>(group.checkedChipId)
                filterModel.area = selectedChip.text.toString()
            }
            statusReset(areaStatus, categoryStatus)
        }
        binding.chipgroupBottomshettfilterCategory.setOnCheckedStateChangeListener { group, _ ->
            if (group.checkedChipId == -1) {
                categoryStatus = false
                filterModel.category = null
            } else {
                categoryStatus = true
                val selectedChip = group.findViewById<Chip>(group.checkedChipId)
                filterModel.category = selectedChip.text.toString()
            }
            statusReset(areaStatus, categoryStatus)
        }

        binding.btnBottomsheetfilterReset.setOnClickListener {
            binding.chipgroupBottomshettfilterArea.clearCheck()
            binding.chipgroupBottomshettfilterCategory.clearCheck()

            filterModel = FilterModel()

            areaStatus = false
            categoryStatus = false
            statusReset()
        }

        binding.btnBottomsheetfilterSubmit.setOnClickListener {
            setFragmentResult(FILTER_KEY, bundleOf(RESULT_FILTER_KEY to filterModel))
            dismiss()
        }
    }

    private fun setUpObserver() {
        viewModel.resultArea.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.shimmerBottomsheetfilterArea.startShimmer()
                    binding.shimmerBottomsheetfilterArea.visibility = View.VISIBLE
                }

                is ViewState.Error -> {
                    binding.shimmerBottomsheetfilterArea.visibility = View.GONE
                    binding.shimmerBottomsheetfilterArea.stopShimmer()
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }
                is ViewState.Success -> {
                    binding.shimmerBottomsheetfilterArea.visibility = View.GONE
                    binding.shimmerBottomsheetfilterArea.stopShimmer()
                    it.data.forEach { item ->
                        Utils.addChip(
                            binding.chipgroupBottomshettfilterArea,
                            item.strArea,
                            requireActivity(),
                            true
                        )
                    }
                    if (filterModel.area != null) {
                        binding.chipgroupBottomshettfilterArea.forEach { chip ->
                            if ((chip as Chip).text == filterModel.area) {
                                areaStatus = true
                                chip.isChecked = true
                            }
                        }

                        statusReset(areaStatus, categoryStatus)
                    }
                }
            }
        }

        viewModel.resultCategory.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.shimmerBottomsheetfilterCategory.startShimmer()
                    binding.shimmerBottomsheetfilterCategory.visibility = View.VISIBLE
                }

                is ViewState.Error -> {
                    binding.shimmerBottomsheetfilterCategory.visibility = View.GONE
                    binding.shimmerBottomsheetfilterCategory.stopShimmer()
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }
                is ViewState.Success -> {
                    binding.shimmerBottomsheetfilterCategory.visibility = View.GONE
                    binding.shimmerBottomsheetfilterCategory.stopShimmer()
                    it.data.forEach { item ->
                        Utils.addChip(
                            binding.chipgroupBottomshettfilterCategory,
                            item.strCategory,
                            requireActivity(),
                            true
                        )
                    }

                    if (filterModel.category != null) {
                        binding.chipgroupBottomshettfilterCategory.forEach { chip ->
                            if ((chip as Chip).text == filterModel.category) {
                                categoryStatus = true
                                chip.isChecked = true
                            }
                        }
                        statusReset(areaStatus, categoryStatus)
                    }
                }
            }
        }
    }

    private fun statusReset(
        areaStatus: Boolean = false,
        categoryStatus: Boolean = false,
    ) {
        binding.btnBottomsheetfilterReset.isVisible =
            areaStatus || categoryStatus
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ModalFilterBottomSheet"
        const val FILTER_KEY = "RequestKey"
        const val RESULT_FILTER_KEY = "BundleKey"
    }
}