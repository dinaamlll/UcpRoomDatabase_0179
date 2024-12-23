package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp

object PenyediaMataKuliahViewModel{

    val Factory = viewModelFactory {
        initializer {
            MatakuliahViewModel(
                krsApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            HomeMatakuliahViewModel(
                krsApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            DetailMatakuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMataKuliah,
            )
        }
        initializer {
            UpdateMatakuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMataKuliah,
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)