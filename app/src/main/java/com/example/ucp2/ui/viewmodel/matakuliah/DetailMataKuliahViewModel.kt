package com.example.ucp2.ui.viewmodel.matakuliah


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMataKuliah
import com.example.ucp2.ui.navigation.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMataKuliah,
) : ViewModel() {
    // Extracting the 'kode' from saved state to identify the Matakuliah
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetail.KODE])

    // State flow to hold Matakuliah details
    val detailUiState: StateFlow<DetailUiState> = repositoryMatakuliah.getMataKuliah(_kode)
        .filterNotNull()
        .map { matakuliah ->
            DetailUiState(
                detailUiEvent = matakuliah.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)  // Optional delay to simulate loading
        }
        .catch { exception ->
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = exception.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(isLoading = true),
        )

    // Function to delete a Matakuliah
    fun deleteMataKuliah() {
        detailUiState.value.detailUiEvent.toMatakuliahEntity().let {
            viewModelScope.launch {
                repositoryMatakuliah.deleteMataKuliah(it)
            }
        }
    }
}

// Data class to manage the UI state for Matakuliah details
data class DetailUiState(
    val detailUiEvent: MatakuliahEvent = MatakuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MatakuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MatakuliahEvent()
}


fun MataKuliah.toDetailUiEvent(): MatakuliahEvent {
    return MatakuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        dosenPengampu = dosenPengampu
    )
}
