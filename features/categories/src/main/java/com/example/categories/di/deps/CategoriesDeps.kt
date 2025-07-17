package com.example.categories.di.deps

import com.example.network.apiService.CategoryApiService


interface CategoriesDeps {

    var categoryApiService: CategoryApiService
}