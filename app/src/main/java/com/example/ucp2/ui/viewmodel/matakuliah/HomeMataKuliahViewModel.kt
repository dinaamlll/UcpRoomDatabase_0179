package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMataKuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMatakuliahViewModel(
    private val repositoryMatakuliah: RepositoryMataKuliah
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = repositoryMatakuliah.getAllMataKuliah()
        .filterNotNull()
        .map {
            HomeUiState(
                listMatakuliah = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(
                isLoading = true,
            )
        )
}

data class HomeUiState(
    val listMatakuliah: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)