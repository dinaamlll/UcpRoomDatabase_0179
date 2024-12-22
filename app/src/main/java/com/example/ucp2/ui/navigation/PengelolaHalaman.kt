package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dosen.DetailDsnView
import com.example.ucp2.ui.view.dosen.HomeDsnView
import com.example.ucp2.ui.view.dosen.InsertDsnView
import com.example.ucp2.ui.view.matakuliah.DestinasiMatakuliahInsert
import com.example.ucp2.ui.view.matakuliah.DetailMatakuliahView
import com.example.ucp2.ui.view.matakuliah.HomeMatakuliahView
import com.example.ucp2.ui.view.matakuliah.InsertMatakuliahView
import com.example.ucp2.ui.view.matakuliah.UpdateMatakuliahView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHome.route) {

        // Menu Selection
        composable(route = DestinasiHome.route) {
            HomeView(
                onDosenClick = {
                    navController.navigate(DestinasiDosen.route)
                },
                onMataKuliahClick = {
                    navController.navigate(DestinasiMatakuliah.route)
                },
                modifier = modifier
            )
        }

        // Dosen Screens
        composable(route = DestinasiDosen.route) {
            HomeDsnView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDosenDetail.route}/$nidn")
                    println(
                        "PengelolaHalaman: nidn = $nidn"
                    )
                },
                onAddDsn = {
                    navController.navigate(DestinasiDosen.route)
                },
                modifier = modifier
            )
        }

        composable(route = "dosen/create") {
            InsertDsnView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(
            DestinasiDosenDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDosenDetail.NIDN) {
                    type = NavType.StringType
                }
            )
        ) {
            val nidn = it.arguments?.getString(DestinasiDosenDetail.NIDN)
            nidn?.let { nidn ->
                DetailDsnView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }

        // Mata Kuliah Screens
        composable(route = DestinasiMatakuliah.route) {
            HomeMatakuliahView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiMatakuliahDetail.route}/$kode")
                    println("PengelolaHalaman = $kode")
                },
                onBack = { navController.popBackStack() },
                onAddMatakuliah = {
                    navController.navigate(DestinasiMatakuliahInsert.route)
                },
                modifier = modifier
            )
        }


        composable(route = DestinasiMatakuliahInsert.route) {
            InsertMatakuliahView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(
            DestinasiMatakuliahDetail.routesWithArg,
            arguments = listOf(
                navArgument("kode") { type = NavType.StringType }
            )
        ) {
            val kode = it.arguments?.getString("kode")
            kode?.let {kode->
                DetailMatakuliahView(
                    onBack = { navController.popBackStack() },
                    onEditClick = {
                        navController.navigate("matakuliah/update/$kode")
                    },
                    onCreateClick = {
                        navController.navigate("matakuliah/delete/$kode")
                    },
                    modifier = modifier
                )
            }
        }

        composable(
            DestinasiMatakuliahUpdate.routesWithArg,
            arguments = listOf(
                navArgument("kode") { type = NavType.StringType }
            )
        ) {
            UpdateMatakuliahView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        composable(
            route = "matakuliah/delete/{kode}",
            arguments = listOf(
                navArgument("kode") { type = NavType.StringType }
            )
        ) {
            DeleteMatakuliahView(
                onBack = { navController.popBackStack() },
                modifier = modifier
            )
        }
    }
}
