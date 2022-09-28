package org.d3if43.jagakandang.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class IotDataClass(
    @get:Exclude
    var id: String= "",
    var nama_perangkat: String = "",
    var lokasi_perangkat: String = "",
    var luas_lokasi: String = "",
    var humidity: String = "",
    var humidity_min: String = "",
    var humidity_max: String = "",
    var temperature: String = "",
    var temperature_min: String = "",
    var temperature_max: String = "",
    var amonia: String = "",
    var amonia_max: String = "",
    var wifi_config: String = "",
    var automation_status: String = "",
    var channel_1: String = "",
    var channel_2: String = ""
) : Parcelable
