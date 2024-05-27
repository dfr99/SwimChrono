package es.udc.apm.swimchrono.model

import android.location.Address
import android.provider.ContactsContract.CommonDataKinds.Phone
import java.net.URL

data class Club(
    val id: Int,
    val name: String,
    val city: String,
    val address: String,
    val phone: String,
    val url: String,
    val trainers: List<String>,
    val members: List<String>,
    val membersNumber: Int
)
