package es.udc.apm.swimchrono.model

import java.util.Date

data class Race(
    val id: Int,
    val swimmer: String,
    val club: String,
    val race: String,
    val heat: Int,
    val lane: Int,
    val hour: Date
)