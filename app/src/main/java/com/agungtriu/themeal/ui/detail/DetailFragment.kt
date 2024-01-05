package com.agungtriu.themeal.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agungtriu.themeal.R
import com.agungtriu.themeal.data.local.MealEntity
import com.agungtriu.themeal.databinding.FragmentDetailBinding
import com.agungtriu.themeal.ui.base.BaseFragment
import com.agungtriu.themeal.utils.ViewState
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var adapter: DetailAdapter
    private var loadLocal = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserveData()
        setUpAdapter()
        setUpListener()
    }

    private fun setUpAdapter() {
        adapter = DetailAdapter(lifecycle)
        binding.vpDetail.adapter = adapter

        TabLayoutMediator(binding.tlDetail, binding.vpDetail) { tab, _ ->
            tab.setCustomView(R.layout.custom_tab_indicator)
        }.attach()
    }

    private fun setUpListener() {
        binding.toolbarDetail.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btn_detail_bookmark -> {
                    if (viewModel.isBookmark) {
                        viewModel.deleteMeal()
                    } else {
                        viewModel.insertMeal()
                    }
                    true
                }

                else -> false
            }
        }

        binding.includeDetailError.btnErrorResetRefresh.setOnClickListener {
            viewModel.getDetail()
        }

        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpObserveData() {
        viewModel.resultSelect.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    if (loadLocal) {
                        binding.includeDetailError.constraintError.visibility = View.GONE
                        binding.shimmerDetail.startShimmer()
                        binding.shimmerDetail.visibility = View.VISIBLE
                        binding.constraintDetailContent.visibility = View.GONE
                    }
                }

                is ViewState.Error -> {
                    if (it.error == "null") {
                        binding.toolbarDetail.menu.findItem(R.id.btn_detail_bookmark)
                            .setIcon(R.drawable.ic_bookmark_border)
                        if (loadLocal) {
                            binding.shimmerDetail.stopShimmer()
                            binding.shimmerDetail.visibility = View.GONE
                            binding.includeDetailError.constraintError.visibility = View.VISIBLE
                            binding.includeDetailError.tvErrorDesc.text = "Data Not Found"
                        }

                    } else {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                    }
                }

                is ViewState.Success -> {
                    binding.toolbarDetail.menu.findItem(R.id.btn_detail_bookmark)
                        .setIcon(R.drawable.ic_bookmark)

                    if (loadLocal) {
                        displayContent(it.data)
                    }
                }
            }
        }

        viewModel.resultDetail.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.includeDetailError.constraintError.visibility = View.GONE
                    binding.shimmerDetail.startShimmer()
                    binding.shimmerDetail.visibility = View.VISIBLE
                    binding.constraintDetailContent.visibility = View.GONE
                }

                is ViewState.Error -> {
                    viewModel.selectMeal()
                    loadLocal = true
                    binding.shimmerDetail.stopShimmer()
                    binding.shimmerDetail.visibility = View.GONE
                    binding.includeDetailError.constraintError.visibility = View.VISIBLE
                    binding.includeDetailError.tvErrorDesc.text = it.error
                }

                is ViewState.Success -> {
                    loadLocal = false
                    displayContent(it.data.toMealEntity())
                }
            }
        }

        viewModel.resultInsert.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {}

                is ViewState.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }

                is ViewState.Success -> {
                    viewModel.isBookmark = true
                    binding.toolbarDetail.menu.findItem(R.id.btn_detail_bookmark)
                        .setIcon(R.drawable.ic_bookmark)
                }
            }
        }

        viewModel.resultDelete.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {}

                is ViewState.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }

                is ViewState.Success -> {
                    viewModel.isBookmark = false
                    binding.toolbarDetail.menu.findItem(R.id.btn_detail_bookmark)
                        .setIcon(R.drawable.ic_bookmark_border)
                }
            }
        }
    }

    private fun displayContent(data: MealEntity) {
        binding.shimmerDetail.stopShimmer()
        binding.shimmerDetail.visibility = View.GONE
        binding.constraintDetailContent.visibility = View.VISIBLE
        binding.toolbarDetail.title = data.strMeal
        binding.tvDetailTitle.text = data.strMeal
        binding.tvDetailArea.text = data.strArea
        binding.tvDetailCategory.text = data.strCategory
        binding.tvDetailInstructionsDesc.text = data.strInstructions
        showIngredient(
            binding.tvDetailIngredientOne,
            data.strMeasure1,
            data.strIngredient1
        )
        showIngredient(
            binding.tvDetailIngredientTwo,
            data.strMeasure2,
            data.strIngredient2
        )
        showIngredient(
            binding.tvDetailIngredientThree,
            data.strMeasure3,
            data.strIngredient3
        )
        showIngredient(
            binding.tvDetailIngredientFour,
            data.strMeasure4,
            data.strIngredient4
        )
        showIngredient(
            binding.tvDetailIngredientFive,
            data.strMeasure5,
            data.strIngredient5
        )
        showIngredient(
            binding.tvDetailIngredientSix,
            data.strMeasure6,
            data.strIngredient6
        )
        showIngredient(
            binding.tvDetailIngredientSeven,
            data.strMeasure7,
            data.strIngredient7
        )
        showIngredient(
            binding.tvDetailIngredientEight,
            data.strMeasure8,
            data.strIngredient8
        )
        showIngredient(
            binding.tvDetailIngredientNine,
            data.strMeasure9,
            data.strIngredient9
        )
        showIngredient(
            binding.tvDetailIngredientTen,
            data.strMeasure10,
            data.strIngredient10
        )
        showIngredient(
            binding.tvDetailIngredientEleven,
            data.strMeasure11,
            data.strIngredient11
        )
        showIngredient(
            binding.tvDetailIngredientTwelve,
            data.strMeasure12,
            data.strIngredient12
        )
        showIngredient(
            binding.tvDetailIngredientThirteen,
            data.strMeasure13,
            data.strIngredient13
        )
        showIngredient(
            binding.tvDetailIngredientFourteen,
            data.strMeasure14,
            data.strIngredient14
        )
        showIngredient(
            binding.tvDetailIngredientFifteen,
            data.strMeasure15,
            data.strIngredient15
        )
        showIngredient(
            binding.tvDetailIngredientSixteen,
            data.strMeasure16,
            data.strIngredient16
        )
        showIngredient(
            binding.tvDetailIngredientSeventeen,
            data.strMeasure17,
            data.strIngredient17
        )
        showIngredient(
            binding.tvDetailIngredientEighteen,
            data.strMeasure18,
            data.strIngredient18
        )
        showIngredient(
            binding.tvDetailIngredientNineteen,
            data.strMeasure19,
            data.strIngredient19
        )
        showIngredient(
            binding.tvDetailIngredientTwenty,
            data.strMeasure20,
            data.strIngredient20
        )
        adapter.submitList(listOf(data.strMealThumb, data.strYoutube))
    }

    private fun showIngredient(textView: TextView, measure: String?, ingredient: String?) {
        if (!measure.isNullOrBlank() && !ingredient.isNullOrBlank()) {
            textView.visibility = View.VISIBLE
            textView.text = measure.plus(" ").plus(ingredient)
        }
    }

    companion object {
        const val ID_KEY = "id"
    }
}