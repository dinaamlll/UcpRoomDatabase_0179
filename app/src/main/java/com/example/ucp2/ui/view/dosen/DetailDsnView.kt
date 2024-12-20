package com.example.ucp2.ui.view.dosen

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
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.costumwidget.CostumTopAppBar

@Composable
fun DetailDsnView(
    modifier: Modifier = Modifier,
    viewModel: DetailDsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onCreateClick: () -> Unit = {} // Ganti onEditClick menjadi onCreateClick
) {
    Scaffold(
        topBar = {
            CostumTopAppBar(
                judul = "Detail Dosen",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,  // Mengganti fungsi untuk menambah dosen
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Tambah Dosen"  // Menyesuaikan deskripsi
                )
            }
        }
    ) { innerPadding ->
        val detailDsnUiState by viewModel.detailDsnUiState.collectAsState()

        BodyDetailDsn(
            modifier = Modifier.padding(innerPadding),
            detailDsnUiState = detailDsnUiState
        )
    }
}
@Composable
fun BodyDetailDsn(
    modifier: Modifier = Modifier,
    detailDsnUiState: DetailDsnUiState = DetailDsnUiState()
) {
    when {
        detailDsnUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Tampilkan loading
            }
        }

        detailDsnUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailDsn(
                    dosen = detailDsnUiState.detailDsnEvent.toDosenEntity(),
                    modifier = Modifier
                )
            }
        }

        detailDsnUiState.isUiEventEmpty -> {
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
fun ItemDetailDsn(
    modifier: Modifier = Modifier,
    dosen: Dosen
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
            ComponentDetailDsn(judul = "NIDN", isinya = dosen.nidn)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailDsn(judul = "Nama", isinya = dosen.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailDsn(judul = "Jenis Kelamin", isinya = dosen.jenisKelamin)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}
@Composable
fun ComponentDetailDsn (
    modifier: Modifier = Modifier,
    judul : String,
    isinya : String,
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
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,

            )
    }
}
