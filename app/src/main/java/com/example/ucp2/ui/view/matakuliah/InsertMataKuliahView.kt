package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.costumwidget.CostumTopAppBar
import com.example.ucp2.ui.costumwidget.DynamicSelectedTextField
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.dosen.HomeDsnViewModel
import com.example.ucp2.ui.viewmodel.dosen.HomeUiState
import com.example.ucp2.ui.viewmodel.dosen.PenyediaDsnViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahUIState
import com.example.ucp2.ui.viewmodel.matakuliah.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaMataKuliahViewModel
import kotlinx.coroutines.launch

object DestinasiMatakuliahInsert : AlamatNavigasi {
    override val route = "matakuliahinsert"
}

@Composable
fun InsertMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatakuliahViewModel = viewModel(factory = PenyediaMataKuliahViewModel.Factory),
    viewModelDosen: HomeDsnViewModel = viewModel(factory = PenyediaDsnViewModel.Factory),
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val DaftarDosen by viewModelDosen.homeUiState.collectAsState()
    // Observe snackBarMessage changes
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            CostumTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah"
            )
            InsertBodyMatakuliah(
                uiState = uiState,
                ListDosen = DaftarDosen,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMatakuliah(
    modifier: Modifier = Modifier,
    onValueChange: (MatakuliahEvent) -> Unit,
    uiState: MatakuliahUIState,
    onClick: () -> Unit,
    ListDosen: HomeUiState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            listDosen = ListDosen.listDosen
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMatakuliah(
    matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    onValueChange: (MatakuliahEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    listDosen: List<Dosen>
) {
    val semesterOptions = listOf("Genap", "Ganjil")
    val jenisOptions = listOf("Wajib", "Pilihan")
    val DaftarDsn = listDosen.map { it.nama }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.kode,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(kode = it))
            },
            label = { Text("Kode Matakuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan kode matakuliah") },
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.nama,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(nama = it))
            },
            label = { Text("Nama Matakuliah") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama Matakuliah") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.sks,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan jumlah SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Semester")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            semesterOptions.forEach { semester ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.semester == semester,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(semester = semester))
                        },
                    )
                    Text(text = semester)
                }
            }
        }
        Text(
            text = errorState.semester ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Jenis Matakuliah")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenisOptions.forEach { jenis ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.jenis == jenis,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(jenis = jenis))
                        },
                    )
                    Text(text = jenis)
                }
            }
        }
        Text(
            text = errorState.jenis ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Dosen Pengampu")
        DynamicSelectedTextField(
            selectedValue = matakuliahEvent.dosenPengampu,
            options = DaftarDsn,
            label = "Pilih Dosen Pengampu",
            onValueChangedEvent = {
                onValueChange(matakuliahEvent.copy(dosenPengampu = it))
            }
        )
        Text(
            text = errorState.dosenPengampu ?: "",
            color = Color.Red
        )
    }
}