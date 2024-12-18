package com.example.ucp2.repository

import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMataKuliah {
        suspend fun insertMataKuliah(mataKuliah: MataKuliah) // CREATE

        fun getAllMataKuliah(): Flow<List<MataKuliah>> // READ

        fun getMataKuliah(kode: String): Flow<MataKuliah> // READ

        suspend fun deleteMataKuliah(mataKuliah: MataKuliah) // DELETE

        suspend fun updateMataKuliah(mataKuliah: MataKuliah) // UPDATE
    }
