package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FormMatakuliah(
    matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    onValueChange: (MatakuliahEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val semesterOptions = listOf("Semester 1", "Semester 2", "Semester 3", "Semester 4")
    val jenisOptions = listOf("Wajib", "Pilihan")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.kodeMatakuliah,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(kodeMatakuliah = it))
            },
            label = { Text("Kode Matakuliah") },
            isError = errorState.kodeMatakuliah != null,
            placeholder = { Text("Masukkan kode matakuliah") },
        )
        Text(
            text = errorState.kodeMatakuliah ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.namaMatakuliah,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(namaMatakuliah = it))
            },
            label = { Text("Nama Matakuliah") },
            isError = errorState.namaMatakuliah != null,
            placeholder = { Text("Masukkan nama matakuliah") },
        )
        Text(
            text = errorState.namaMatakuliah ?: "",
            color = Color.Red
        )

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

        Text(text = "Jenis")
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

        // Dropdown for selecting Dosen Pengampu
        Text(text = "Dosen Pengampu")
        DropdownMenu(
            expanded = uiState.isDropdownExpanded,
            onDismissRequest = { onValueChange(matakuliahEvent.copy(isDropdownExpanded = false)) }
        ) {
            uiState.dosenList.forEach { dosen ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(matakuliahEvent.copy(dosenPengampu = dosen))
                    }
                ) {
                    Text(text = dosen.nama)
                }
            }
        }
        Text(
            text = errorState.dosenPengampu ?: "",
            color = Color.Red
        )
    }
}