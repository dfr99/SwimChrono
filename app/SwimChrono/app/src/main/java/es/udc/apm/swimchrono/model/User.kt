package es.udc.apm.swimchrono.model

data class User(
    val name: String,
    val surname: String,
    val telefono: String,
    val fecha_nacimiento: String,
    val role: String,
    val password: String,
    val email: String,
) {
    constructor() : this("", "", "", "", "", "", "")
}