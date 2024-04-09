package es.udc.apm.swimchrono.model

data class Tournament(
    val id: Int,
    val type: String,
    val name: String,
    val date: String,
    val participants: Int,
    val location: String,
    val races: List<String>
)