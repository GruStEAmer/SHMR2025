package com.example.shmr.presentation.model

import com.example.shmr.domain.model.category.Category

data class CategoryUi(
    val id: Int,
    val name: String,
    val emoji: String,
)

fun Category.toCategoryUi(): CategoryUi {
    return CategoryUi(
        id = this.id,
        name = this.name,
        emoji = this.emoji
    )
}