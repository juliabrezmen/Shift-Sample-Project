package com.juliadanylyk.shift.db

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.juliadanylyk.shift.data.Shift

@Dao
interface ShiftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shifts: List<Shift>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shift: Shift)

    @Query("SELECT * FROM " + ShiftDatabase.SHIFTS_TABLE_NAME)
    fun getShifts(): DataSource.Factory<Int, Shift>
}