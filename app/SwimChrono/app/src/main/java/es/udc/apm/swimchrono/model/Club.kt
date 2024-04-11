package es.udc.apm.swimchrono.model

import android.location.Address
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.Contacts.Photo
import java.net.URL
import java.util.Date

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val photo: Photo?,
    val email: Email,
    val birthDate: Date,
    val role: String,
    val club: Int
)

data class Club(
    val id: Int,
    val name: String,
    val region: String,
    val address: Address,
    val phone: Phone,
    val membersNumber: Int,
    val url: URL,
    val members: List<User>,
)
