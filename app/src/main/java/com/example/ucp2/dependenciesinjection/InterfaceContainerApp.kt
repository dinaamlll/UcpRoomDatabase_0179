package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.KrsDatabase
import com.example.ucp2.repository.LocalRepositoryDsn
import com.example.ucp2.repository.LocalRepositoryMataKuliah
import com.example.ucp2.repository.RepositoryDsn
import com.example.ucp2.repository.RepositoryMataKuliah

interface InterfaceContainerApp {
    val repositoryDsn: RepositoryDsn
    val repositoryMataKuliah: RepositoryMataKuliah
}

class ContainerApp(private val context: Context): InterfaceContainerApp {
    override val repositoryDsn: RepositoryDsn by lazy {
        LocalRepositoryDsn(KrsDatabase.getDatabase(context).dosenDao())
    }

    override val repositoryMataKuliah: RepositoryMataKuliah by lazy {
        LocalRepositoryMataKuliah(KrsDatabase.getDatabase(context).mataKuliahDao())
    }
}