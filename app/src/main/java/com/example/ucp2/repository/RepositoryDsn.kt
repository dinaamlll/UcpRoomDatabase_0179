package com.example.ucp2.repository

import com.example.ucp2.data.entity.Dosen

interface RepositoryDsn {
    suspend fun insertDsn(dosen: Dosen)
}