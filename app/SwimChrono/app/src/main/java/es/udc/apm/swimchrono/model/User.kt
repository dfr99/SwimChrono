package es.udc.apm.swimchrono.model

data class User(
    val UID: String,
    val name: String,
    val surname: String,
    val numero_telefono: String,
    val fecha_nacimiento: String,
    val role: String,
    val dni: String,
    val email: String,
) {
    constructor() : this("", "", "", "", "", "", "", "")
}