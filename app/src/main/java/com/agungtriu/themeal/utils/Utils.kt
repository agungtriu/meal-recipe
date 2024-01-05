package com.agungtriu.themeal.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.agungtriu.themeal.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

object Utils {
    fun closeSoftKeyboard(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun addChip(chipGroup: ChipGroup, text: String?, context: Context, isCheckable: Boolean) {
        val chip = Chip(context)
        chip.setTextAppearanceResource(R.style.Theme_TheMeal_ChipGroup_Chip)
        chip.text = text
        chip.setChipBackgroundColorResource(R.color.colorChip)
        chip.isCheckable = isCheckable
        chipGroup.addView(chip)
    }
}