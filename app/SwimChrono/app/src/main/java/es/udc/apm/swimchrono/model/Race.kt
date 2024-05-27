package es.udc.apm.swimchrono.model

import java.util.Date


data class Race(
    val id: Int,
    val style: String,
    val category: String,
    val distance: String,
    val heat: Int,
    val lane: Int,
    val hour: Date?,
    val times: Map<String, String>,
)