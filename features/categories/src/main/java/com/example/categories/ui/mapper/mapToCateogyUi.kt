package com.example.categories.ui.mapper

import com.example.categories.ui.model.CategoryUi
import com.example.network.model.category.Category

fun Category.toCategoryUi() =
    CategoryUi(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )