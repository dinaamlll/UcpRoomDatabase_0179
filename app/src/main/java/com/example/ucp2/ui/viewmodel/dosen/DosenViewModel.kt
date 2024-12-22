package com.example.ucp2.ui.viewmodel.dosen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDsn
import kotlinx.coroutines.launch


class DosenViewModel(
    private val repositoryDsn: RepositoryDsn): ViewModel() {
    var uiState by mutableStateOf(DsnUIState())

    //Memperbarui state berdasarkan input pengguna (mengubah tampilan di text field)
    fun updateState(mahasiswaEvent: DosenEvent) {
        uiState = uiState.copy(
            dosenEvent = mahasiswaEvent,
        )
    }
    private fun validateFields(): Boolean {
        val event = uiState.dosenEvent
        val errrorState = FormErrrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errrorState)
        return errrorState.isValid()
    }
    fun saveData() {
        val currentEvent = uiState.dosenEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDsn.insertDsn(currentEvent.toMahasiswaEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dosenEvent = DosenEvent(), // reset input form
                        isEntryValid = FormErrrorState() //reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class DsnUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrrorState = FormErrrorState(),
    val snackBarMessage: String? = null,
)
data class FormErrrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
) {
    fun isValid(): Boolean {
        return nidn == null && nama == null && jenisKelamin == null
    }
}
//data classvariable yang menyimpan data input form
data class DosenEvent(
    val nidn: String = "",
    val nama: String = "",
    val jenisKelamin: String = ""
)
//menyimpan input form ke dalam entity
fun DosenEvent.toMahasiswaEntity(): Dosen = Dosen(
    nidn = nidn, //yg kanan entitas, yg kiri mahasiswaEvent
    nama = nama,
    jenisKelamin = jenisKelamin
)
