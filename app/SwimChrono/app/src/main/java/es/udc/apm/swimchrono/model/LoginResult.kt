package es.udc.apm.swimchrono.model

sealed class LoginResult {
    data class Success(val userId: String) : LoginResult()
    object InvalidEmail : LoginResult()
    object InvalidPassword : LoginResult()
    object NetworkError : LoginResult()
    object UnknownError : LoginResult()
}
