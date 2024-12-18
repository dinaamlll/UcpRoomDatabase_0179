package com.example.ucp2.repository

import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDsn {
    suspend fun insertDsn(dosen: Dosen)
    fun getAllDosen() : Flow<List<Dosen>>
    fun getDosen(nim: String) : Flow<Dosen>
}