package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.dao.MataKuliahDao
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.MataKuliah

@Database(entities = [Dosen::class, MataKuliah::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase() {

    // Akses ke Dosen dan MataKuliah Dao
    abstract fun dosenDao(): DosenDao
    abstract fun mataKuliahDao(): MataKuliahDao

    companion object {
        @Volatile
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KrsDatabase::class.java,
                    "KrsDatabase" // Nama database
                )
                    .build().also { Instance = it }
            })
        }
    }
}