package com.juliadanylyk.shift.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.juliadanylyk.shift.db.ShiftDatabase
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = ShiftDatabase.SHIFTS_TABLE_NAME)
data class Shift(@PrimaryKey(autoGenerate = true) val id: Int,
                 val startTime: Long,
                 val endTime: Long?,
                 val startLatitude: Double,
                 val startLongitude: Double,
                 val endLatitude: Double?,
                 val endLongitude: Double?,
                 val image: String) : Parcelable {

    fun inProgress() = endTime == null
}