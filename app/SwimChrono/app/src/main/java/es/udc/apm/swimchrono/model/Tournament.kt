package es.udc.apm.swimchrono.model

import java.util.Date

data class Tournament(
    val id: Int,
    val type: String,
    val name: String,
    val date: Date?,
    val participants: Int,
    val location: String,
    val races: List<Race>,
)