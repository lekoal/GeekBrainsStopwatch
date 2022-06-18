package com.example.geekbrainsstopwatch.di

import com.example.geekbrainsstopwatch.view.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val koinDataSourceModule = module {
    viewModel(named("main_view_model")) {
        MainViewModel()
    }
}