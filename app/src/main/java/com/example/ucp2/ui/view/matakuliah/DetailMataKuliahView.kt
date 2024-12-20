package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.costumwidget.CostumTopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel

@Composable
fun DetailMatakuliahView(
    modifier: Modifier = Modifier,
    viewModel: DetailMatakuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onCreateClick: () -> Unit = {} // Changed onEditClick to onCreateClick
) {
    Scaffold(
        topBar = {
            CostumTopAppBar(
                judul = "Detail Matakuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,  // Changed function for creating new Matakuliah
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Tambah Matakuliah"  // Updated description
                )
            }
        }
    ) { innerPadding ->
        val detailMatakuliahUiState by viewModel.detailMatakuliahUiState.collectAsState()

        BodyDetailMatakuliah(
            modifier = Modifier.padding(innerPadding),
            detailMatakuliahUiState = detailMatakuliahUiState
        )
    }
}

@Composable
fun BodyDetailMatakuliah(
    modifier: Modifier = Modifier,
    detailMatakuliahUiState: DetailMatakuliahUiState = DetailMatakuliahUiState()
) {
    when {
        detailMatakuliahUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Show loading indicator
            }
        }

        detailMatakuliahUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMatakuliah(
                    matakuliah = detailMatakuliahUiState.detailMatakuliahEvent.toMatakuliahEntity(),
                    modifier = Modifier
                )
            }
        }

        detailMatakuliahUiState.isUiEventEmpty -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailMatakuliah(
    modifier: Modifier = Modifier,
    matakuliah: MataKuliah
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMatakuliah(judul = "Kode Matakuliah", isinya = matakuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatakuliah(judul = "Nama", isinya = matakuliah.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatakuliah(judul = "SKS", isinya = matakuliah.sks.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatakuliah(judul = "Semester", isinya = matakuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailMatakuliah(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
