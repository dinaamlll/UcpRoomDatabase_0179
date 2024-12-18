package com.example.ucp2.repository

import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.entity.Dosen

class LocalRepositoryDsn (
    private val dosenDao: DosenDao
) : RepositoryDsn {
    override suspend fun insertDsn(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }
}