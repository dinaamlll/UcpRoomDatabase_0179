package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMataKuliah
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeMatakuliahViewModel(
    private val repositoryMatakuliah: RepositoryMataKuliah
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState(isLoading = true))
    val homeUiState: StateFlow<HomeUiState> get() = _homeUiState

    init {
        getMataKuliahData()
    }

    private fun getMataKuliahData() {
        // Mengambil data dari repository dan mengubah UI state berdasarkan hasilnya
        viewModelScope.launch {
            repositoryMatakuliah.getAllMataKuliah()
                .onStart {
                    _homeUiState.value = HomeUiState(isLoading = true) // Set isLoading = true
                }
                .catch { exception ->
                    _homeUiState.value = HomeUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = exception.message ?: "Terjadi kesalahan"
                    ) // Tangani error
                }
                .collect { mataKuliahList ->
                    _homeUiState.value = HomeUiState(
                        isLoading = false,
                        listMatakuliah = mataKuliahList
                    ) // Update state dengan data mata kuliah
                }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val listMatakuliah: List<MataKuliah> = emptyList()
)
