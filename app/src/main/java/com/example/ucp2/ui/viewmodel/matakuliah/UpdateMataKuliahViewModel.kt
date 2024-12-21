package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMataKuliah
import com.example.ucp2.ui.viewmodel.dosen.FormErrrorState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMataKuliah
) : ViewModel() {
    var updateUIState by mutableStateOf(MatakuliahUIState())
    private val _kodeMatakuliah: String = checkNotNull(savedStateHandle[DestinasiUpdate.KODE_MATAKULIAH])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMatakuliah.getMataKuliah(_kodeMatakuliah)
                .filterNotNull()
                .first()
                .toUIStateMatakuliah()
        }
    }

    fun updateState(matakuliahEvent: MatakuliahEvent) {
        updateUIState = updateUIState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.matakuliahEvent
        val errorState = FormErrrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode Matakuliah tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong"
        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.matakuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatakuliah.updateMatakuliah(currentEvent.toMatakuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        matakuliahEvent = MatakuliahEvent(),
                        isEntryValid = FormErrrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal di update"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUIStateMatakuliah(): MatakuliahUIState = MatakuliahUIState(
    matakuliahEvent = this.toDetailUiEvent()
)
