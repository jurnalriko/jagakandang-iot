package org.d3if43.jagakandang.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class HomeDataClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var namaKandang: String,
    var lokasi: String,
    var populasi: String,
    var kematian: String,
    var jumlahPakan: String,
    var tglChickIn: String,
    var keterangan: String
): Parcelable
