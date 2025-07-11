package com.example.income.di.deps

interface IncomeDepsProvider {

    var incomeDeps: IncomeDeps

    companion object: IncomeDepsProvider by IncomeDepsStore
}