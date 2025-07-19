package com.example.mapper

import com.example.data.local.entity.LocalCategory
import com.example.data.network.model.category.Category
import com.example.model.CategoryUi

fun Category.toCategoryUi() =
    CategoryUi(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )
fun LocalCategory.toCategoryUi() =
    CategoryUi(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )