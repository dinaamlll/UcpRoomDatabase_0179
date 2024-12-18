package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.ucp2.data.entity.Dosen

@Dao
interface DosenDao {

    @Insert
    suspend fun insertDosen(dosen: Dosen)
}