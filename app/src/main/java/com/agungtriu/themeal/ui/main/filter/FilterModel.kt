package com.agungtriu.themeal.ui.main.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FilterModel(var area: String? = null, var category: String? = null) : Parcelable