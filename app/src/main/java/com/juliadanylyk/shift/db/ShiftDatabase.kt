package com.juliadanylyk.shift.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.juliadanylyk.shift.data.Shift

@Database(entities = [(Shift::class)], version = 1)
abstract class ShiftDatabase : RoomDatabase() {
    abstract fun shiftDao(): ShiftDao

    companion object {
        const val SHIFTS_TABLE_NAME = "SHIFTS_TABLE"
        private const val DATABASE_NAME = "SHIFT_DATABASE"
        fun create(context: Context) = Room.databaseBuilder(context, ShiftDatabase::class.java, DATABASE_NAME).build()
    }
}