package com.example.data.mapper

import com.example.data.local.entity.LocalCategory
import com.example.data.network.model.category.Category

fun Category.toLocalCategory(): LocalCategory {
    return LocalCategory(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome,
        isSynced = true
    )
}

fun LocalCategory.toCategory(): Category {
    return Category(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )
}
