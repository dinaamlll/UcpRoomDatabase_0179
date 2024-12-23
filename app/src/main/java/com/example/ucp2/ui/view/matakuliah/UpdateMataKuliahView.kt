package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.CostumTopAppBar
import com.example.ucp2.ui.viewmodel.dosen.HomeDsnViewModel
import com.example.ucp2.ui.viewmodel.dosen.PenyediaDsnViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaMataKuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.UpdateMatakuliahViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMatakuliahViewModel = viewModel(factory = PenyediaMataKuliahViewModel.Factory),
    viewModelDosen: HomeDsnViewModel = viewModel(factory = PenyediaDsnViewModel.Factory),
) {
    val uiState = viewModel.updateUIState // Get UI state from the ViewModel
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val DaftarDosen by viewModelDosen.homeUiState.collectAsState()
    LaunchedEffect(uiState.snackBarMessage) {
        println("LaunchedEffect triggered")
        uiState.snackBarMessage?.let { message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CostumTopAppBar(
                judul = "Edit Matakuliah",
                showBackButton = true,
                onBack = onBack,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Insert body content
            InsertBodyMatakuliah(
                uiState = uiState,
                ListDosen = DaftarDosen, //Mengambil daftar dosen dari HomeDsnViewModel dan mengirimkannya ke InsertBodyMatakuliah
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // Update state in ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate() // Navigate in the main thread
                            }
                        }
                    }
                }
            )
        }
    }
}
