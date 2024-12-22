package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp
import com.example.ucp2.ui.viewmodel.dosen.DetailDsnViewModel
import com.example.ucp2.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.HomeDsnViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.DetailMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.HomeMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.UpdateMatakuliahViewModel


object PenyediaViewModel {

    val Factory = viewModelFactory {
        // ViewModel untuk Dosen
        initializer {
            DosenViewModel(
                krsApp().containerApp.repositoryDsn
            )
        }
        initializer {
            HomeDsnViewModel(
                krsApp().containerApp.repositoryDsn
            )
        }
        initializer {
            DetailDsnViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryDsn
            )
        }

        // ViewModel untuk MataKuliah
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
                krsApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            UpdateMatakuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMataKuliah
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)
